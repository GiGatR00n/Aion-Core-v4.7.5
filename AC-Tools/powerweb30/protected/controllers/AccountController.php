<?php

class AccountController extends Controller
{
	public $layout='//content';
	
	public function actions() {
		return Config::captcha();
	}
	
	protected function performAjaxValidation($model) {
		if(isset($_POST['ajax']) && $_POST['ajax']==='user-form') {
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}
	}
	
	public $id;
	
	public function actionIndex()
	{
		if (Yii::app()->user->isGuest) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Accounts');
		
		$criteria=new CDbCriteria;
		$criteria->select = 'id, name, access_level, membership, last_ip, expire, '.Yii::app()->params->money.' AS toll, email';
		$criteria->condition = 'id = '.Yii::app()->user->id;
		$model = AccountData::model()->find($criteria);
		
		$ref_link = Yii::app()->homeUrl.'register/'.Yii::app()->user->id;
		
		$transfer = new LogPoints;
		if (isset($_POST['LogPoints']))
		{
			$transfer->attributes = $_POST['LogPoints'];
			$transfer->sender = Yii::app()->user->name;
			
			if($transfer->validate())
			{
				$criteria=new CDbCriteria;
				$criteria->select = 'account_id, account_name, online';
				$criteria->condition = 'name = "'.$transfer->recipient.'"';
				$account_id = Players::model()->find($criteria);
				if (!$account_id) {
					Yii::app()->user->setFlash('message', '<div class="flash_error">'.Yii::t('account', 'Error - Character not found.').'</div>');
					$this->redirect('account');
				}
			
				$criteria=new CDbCriteria;
				$criteria->select = Yii::app()->params->money;
				$criteria->condition = 'id = '.Yii::app()->user->id;
				$balance = AccountData::model()->find($criteria);
				
				if ($transfer->sum > $balance[Yii::app()->params->money]) {
					Yii::app()->user->setFlash('message', '<div class="flash_error">'.Yii::t('account', 'Error - Insufficient funds.').'</div>');
					$this->redirect('account');
				}
				
				$criteria=new CDbCriteria;
				$criteria->condition = 'account_id = '.Yii::app()->user->id.' AND online = 1';
				$check_online = Players::model()->count($criteria);
				if ($check_online > 0) {
					Yii::app()->user->setFlash('message', '<div class="flash_error">'.Yii::t('account', 'Error - You must first be logged out from the game server.').'</div>');
					$this->redirect('account');
				}
				
                $criteria = new CDbCriteria;
				$criteria->condition = 'id = '.Yii::app()->user->id;
				$money = AccountData::model()->find($criteria);
				$money[Yii::app()->params->money] = $money[Yii::app()->params->money] - $transfer->sum;
				$money->save();
				
				$criteria = new CDbCriteria;
				$criteria->condition = 'id = '.$account_id->account_id;
				$money = AccountData::model()->find($criteria);
				$money[Yii::app()->params->money] = $money[Yii::app()->params->money] + $transfer->sum;
				$money->save();
				$transfer->recipient = $account_id->account_name;
				$transfer->save(false);
				
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('account', 'Points listed!').'</div>');
				$this->redirect('account');
			}
		}
		
		$this->render('account', array(
			'model' => $model,
			'ref_link' => $ref_link,
			'form' => $transfer,
		));
	}
	
	public function actionLogout()
	{
		Yii::app()->user->logout();
		$this->redirect(Yii::app()->homeUrl);
	}
	
	public function actionRegister()
	{
		$this->pageTitle = Yii::t('title', 'Registration');
		
		$settings = Settings::model()->find();
		$form = new AccountData();
		$form->scenario = 'register';
		
		if (isset($_GET['id']))
		{
			$id = $_GET['id'];
			$cookie = new CHttpCookie('pow_referal', $id);
			$cookie->expire = time()+60*60*24; 
			Yii::app()->request->cookies['pow_referal'] = $cookie;
		}
		
		if(isset($_POST['AccountData']))
		{
			$form->attributes = $_POST['AccountData'];
			
			if($form->save())
			{
				if ($settings->referal_enable == 1 AND isset(Yii::app()->request->cookies['pow_referal']->value))
				{
					$master = AccountData::model()->findByPk(Yii::app()->request->cookies['pow_referal']->value);
					$model = new LogReferals;
					$model->master = $master->name;
					$model->master_id = $master->id;
					$model->slave = $form->name;
					$model->slave_id = $form->id;
					$model->save(false);
					unset(Yii::app()->request->cookies['pow_referal']);
				}
				
				if ($settings->trial_enable == 1)
				{
					$model = AccountData::model()->findByPk($form->id);
					$model->scenario = 'membership';
					$model->membership = $settings->trial_type;
					$model->expire = date("Y-m-d", mktime() + ($settings->trial_days * 86400));
					$model->save(false);
				}
				
				if ($settings->email_activation == 1)
				{
					$code = substr(md5(uniqid(rand(), true)), 0, rand(10, 15));
					$link = '<a href="'.Yii::app()->homeUrl.'account/activation/'.$code.'">'.Yii::app()->homeUrl.'account/activation/'.$code.'</a>';
					
					$model = AccountData::model()->findByPk($form->id);
					$model->activated = 0;
					$model->activation = $code;
					$model->save(false);
					
					$to = $model->email;
					$subject = 'Account activation';
					$message = 'You have successfully registered as <a href="'.Yii::app()->homeUrl.'">'.Yii::app()->name.'</a><br /><br />To activate your account, click the following link: '.$link;
					
					$headers  = 'MIME-Version: 1.0' . "\r\n";
					$headers .= 'Content-type: text/html; charset=UTF-8' . "\r\n";
					
					$headers .= 'To: '.$model->name.' <'.$model->email.'>' . "\r\n";
					$headers .= 'From: '.Yii::app()->name.' <'.Yii::app()->params['adminEmail'].'>' . "\r\n";
					mail($to, $subject, $message, $headers);
					
					Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('account', 'Account successfully created!  Please check your email inbox for the activation link.').'</div>');
					$this->refresh();
				}
				
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('account', 'Account creation successful!').'</div>');
				$this->refresh();
			}
		}

		$this->render('register', array('form'=>$form));
	}
	
	public function actionActivation($name)
	{
		$model = AccountData::model()->find('activation="'.$name.'"');
		
		if ($model) {
			$model->activation = '';
			$model->activated = 1;
			$model->update();
		}
		else {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('account', 'Your account has been successfully activated!').'</div>');
		$this->render('/message');
	}
	
	public function actionLostPassword()
	{
		if (!Yii::app()->user->isGuest) $this->redirect('account');
		
		$this->pageTitle = Yii::t('title', 'Recover password');
		
		$model = new AccountData();
		$form->model = 'lost';
		
		$this->performAjaxValidation($model);
		
		if(isset($_POST['AccountData']))
		{
			$model->attributes = $_POST['AccountData'];
			
			if($model->validate())
			{
				$account = AccountData::model()->find('name = "'.$model->name.'" AND email = "'.$model->email.'"');
				if ($account != NULL)
				{
					$new_password = uniqid();
					$new_model = AccountData::model()->findByPk($account->id);
					$new_model->password = $this->hashPassword($new_password);
					$new_model->save();
					
					$to = $model->email;
					$subject = Yii::t('account', 'Recover password');
					$message = Yii::t('account', 'Enter your new password').': '.$new_password;
					$headers = 'To: '.$model->name.' <'.$model->email.'>' . "\r\n";
					$headers .= 'From: '.Yii::app()->name.' <'.Yii::app()->params['adminEmail'].'>' . "\r\n";
					mail($to, $subject, $message, $headers);
					
					Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('account', 'Your new password has been emailed to your email address.').'</div>');
					$this->refresh();
				}
				else{
					Yii::app()->user->setFlash('message', '<div class="flash_error">'.Yii::t('account', 'The provided details were not found in the system.').'</div>');
				}
			}
		}
		
		$this->render('lostpassword', array(
			'model' => $model,
		));
	}
	
	public function actionSettings()
	{
		if (Yii::app()->user->isGuest) $this->redirect(Yii::app()->homeUrl);
		
		$this->pageTitle = Yii::t('title', 'Account settings');
		
		$model = AccountData::model()->findByPk(Yii::app()->user->id);
		$model->scenario = 'edit';
		
		$form2 = Players::model()->findAll('account_id = '.Yii::app()->user->id);
		
		if(isset($_POST['AccountData']))
		{
			$model->attributes = $_POST['AccountData'];
			
			if ($this->hashPassword($model->current_password) == $model->password)
			{
				if (!empty($model->new_password))
				{
					$model->password = $this->hashPassword($model->new_password);
				}
				
				if ($model->save()) {
					Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Settings modified!').'</div>');
					$this->refresh();
				}
			}
			else {
				Yii::app()->user->setFlash('message', '<div class="flash_error">'.Yii::t('account', 'Incorrect password!').'</div>');
				$this->refresh();
			}
		}
		
		if(isset($_POST['Players']))
		{
			$clear = Yii::app()->db->createCommand('UPDATE '.Config::db('gs').'.players SET show_location = 0, show_inventory = 0 WHERE account_id = '.Yii::app()->user->id);
			$clear->execute();
			
			if (isset($_POST['Players']['show_location']))
			{
				$command = Yii::app()->db->createCommand('UPDATE '.Config::db('gs').'.players SET show_location = 1 WHERE id=:id');
				foreach ($_POST['Players']['show_location'] as $key=>$value)
				{
					$command->execute(array(':id'=>$key));
				}
			}
			
			if (isset($_POST['Players']['show_inventory']))
			{
				$command = Yii::app()->db->createCommand('UPDATE '.Config::db('gs').'.players SET show_inventory = 1 WHERE id=:id');
				foreach ($_POST['Players']['show_inventory'] as $key=>$value)
				{
					$command->execute(array(':id'=>$key));
				}
			}
			
			Yii::app()->user->setFlash('message2', '<div class="flash_success">'.Yii::t('main', 'Settings modified!').'</div>');
			$this->refresh();
		}
		
		$this->render('settings', array(
			'form'=>$model,
			'form2'=>$form2,
		));
	}
	
	public function actionList()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Accounts list');
		
		$model=new AccountData('search');
		if(isset($_GET['AccountData']))
			$model->attributes=$_GET['AccountData'];
		$this->render('account_list',array(
			'model'=>$model,
		));
	}
	
	public function actionView($name)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Edit accounts').' '.$name;
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->condition = 'name = "'.$name.'"';
		
		$model = AccountData::model()->find($criteria);
		$model->scenario = 'admin_edit';
		
		if(isset($_POST['AccountData']))
		{
			$model->attributes = $_POST['AccountData'];
			
			if (isset($_POST['AccountData']['activated'])) $model->activated = 1;
			else $model->activated = 0;
			if (!empty($model->new_password)) {
				$model->password = $this->hashPassword($model->new_password);
			}
			if (empty($model->expire)) {
				$model->expire = NULL;
			}
			
			if ($model->save()) {
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Accounts modified!').'</div>');
				$this->redirect(Yii::app()->homeUrl.'admin/account/'.$model->name.'/');
			}
		}

		$this->render('account_form', array(
			'model' => $model,
		));
	}
	
	
	public function hashPassword($password)
	{
		return base64_encode(sha1($password, TRUE));
	}
	
}