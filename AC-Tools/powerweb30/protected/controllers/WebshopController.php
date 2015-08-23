<?php







class WebshopController extends Controller
{
	public $layout='//content';
	
	public function actionIndex()
	{
		if (Yii::app()->user->isGuest) $this->redirect(Yii::app()->homeUrl);
		
		$this->pageTitle = Yii::t('title', 'Webshop');
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->order = 'category_id';
		$model = WebshopCategory::model()->findAll($criteria);
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->order = 'id';
		$membership = Membership::model()->findAll($criteria);
		
		if(isset($_POST['Membership']))
		{
			$account = AccountData::model()->findByPk(Yii::app()->user->id);
			$info = Membership::model()->findByPk($_POST['Membership']['id']);
			
			if ($account->membership == 0)
			{
				if ($account[Yii::app()->params->money] >= $info->price)
				{
					$account->membership = $info->type;
					$account->expire = date("Y-m-d", mktime() + ($info->duration * 86400));
					$account[Yii::app()->params->money] = $account[Yii::app()->params->money] - $info->price;
					$account->save();
					
					$log = new LogMembership;
					$log->account = Yii::app()->user->name;
					$log->title = $info->title;
					$log->type = Info::membership($info->type);
					$log->duration = $info->duration;
					$log->price = $info->price;
					$log->save();
					
					Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('webshop', 'Account updated!').'</div>');
					$this->redirect('webshop');
				}
				else
				{
					Yii::app()->user->setFlash('message', '<div class="flash_error">'.Yii::t('webshop', 'Insufficient funds.').'</div>');
					$this->redirect('webshop');
				}
			}
			else
			{
				Yii::app()->user->setFlash('message', '<div class="flash_error">'.Yii::t('webshop', 'You have outdated privileges still active, please update them.').'</div>');
				$this->redirect('webshop');
			}
		}
		
		$this->render('index',array(
			'model'=>$model,
			'membership'=>$membership,
		));
	}
	
	
	public function actionCategory($name)
	{
		if (Yii::app()->user->isGuest) $this->redirect(Yii::app()->homeUrl);
		
		$id = WebshopCategory::model()->find('alt_name = "'.$name.'"');
		$this->pageTitle = Yii::t('title', 'Webshop').': '.$id->name;
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->condition = 'category_id = '.$id->category_id;
		
		$pages=new CPagination(Webshop::model()->count($criteria));
		$pages->pageSize = Config::get('page_shop');
		$pages->applyLimit($criteria);
		
		$model = Webshop::model()->findAll($criteria);
		
		$form = new Inventory();
		$form->scenario = 'buy';
		
		if(isset($_POST['Inventory']))
		{
			$form->attributes=$_POST['Inventory'];
			
			if($form->validate())
			{
				$criteria=new CDbCriteria;
				$criteria->select = '*';
				$criteria->condition = 'item_id = '.$form->item_id;
				$item = Webshop::model()->find($criteria);
				$real_price = ceil($form->new_amount * ($item->price / $item->amount));
				
				$account = AccountData::model()->findByPk(Yii::app()->user->id);
				if ($account[Yii::app()->params->money] < $real_price) {
					Yii::app()->user->setFlash('message', '<div class="flash_error">'.Yii::t('webshop', 'Insufficient funds.').'</div>');
					$this->refresh();
				}
				
				$criteria=new CDbCriteria;
				$criteria->condition = 'id = '.$form->item_owner.' AND online = 1';
				$online = Players::model()->count($criteria);
				if ($online == 1) {
					Yii::app()->user->setFlash('message', '<div class="flash_error">'.Yii::t('webshop', 'Log-out').'</div>');
					$this->refresh();
				}
				
				$criteria=new CDbCriteria;
				$criteria->select = 'MAX(mail_unique_id) as mail_unique_id';
				$last_mail_id = Mail::model()->find($criteria);
				$mail = new Mail;
				$mail->mail_unique_id = $last_mail_id->mail_unique_id + 1;
				$mail->mail_recipient_id = $form->item_owner;
				$mail->sender_name = 'Admin';
				$mail->mail_title = Yii::t('webshop', 'Delivery');
				$mail->mail_message = Yii::t('webshop', 'Your purchase has been successful.  Thank you, and we hope you enjoy the game!');
				$mail->unread = 1;
				if ($form->item_id == 182400001) {
					$mail->attached_item_id = 0;
					$mail->attached_kinah_count = $form->new_amount;
				}
				else {
					$mail->attached_item_id = $this->add_item($form->item_id, $form->item_owner, $form->new_amount);
					$mail->attached_kinah_count = 0;
				}
				$mail->express = 1;
				$mail->save(false);
				
				$account[Yii::app()->params->money] = $account[Yii::app()->params->money] - $real_price;
				$account->save(false);
				
				$log = new LogWebshop;
				$log->player_id = $form->item_owner;
				$log->item = $form->item_id;
				$log->amount = $form->new_amount;
				$log->price = $real_price;
				$log->save(false);
				
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('webshop', 'Purchase successful!').'</div>');
				$this->refresh();
			}
		}
		
		$this->render('category', array(
				'model' => $model,
				'id' => $id,
				'form' => $form,
				'players' => Players::getPlayers(),
				'pages' => $pages,
		));
	}
	
	
	public function add_item($item_id, $item_owner, $amount)
	{
		$criteria=new CDbCriteria;
		$criteria->select = 'MAX(item_unique_id) as item_unique_id';
		$last_item_id = Inventory::model()->find($criteria);
		
		$form = new Inventory();
		$form->scenario = 'buy';
		$form->item_id = $item_id;
		$form->item_owner = $item_owner;
		$form->item_creator = '';
		$form->item_count = $amount;
		$form->item_unique_id = $last_item_id->item_unique_id + 1;
		$form->item_location = 127;
		$form->item_skin = $item_id;
		$form->save(false);
		
		return $form->item_unique_id;
	}
	
	
	public function actionAdd()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Add items to the webshop');
		
		$model = new Webshop();
		
		if(isset($_POST['Webshop']))
		{
			$model->attributes = $_POST['Webshop'];
			$parse = $this->parse($model->item_id);
			
			$model->name = $parse['name'];
			$model->level = $parse['level'];
			
			if($model->save()) {
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('webshop', 'Item added!').'</div>');
				$this->refresh();
			}
		}
		
		$this->render('webshop_form',array(
			'model'=>$model,
		));
	}
	
	public function actionList()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Webshop item list');
		
		$model=new Webshop('search');
		
		if(isset($_GET['Webshop'])) {
			$model->attributes=$_GET['Webshop'];
		}
		
		$this->render('webshop_list',array(
			'model'=>$model,
		));
	}
	
	public function actionEdit($id)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Edit items');
		
		$model = Webshop::model()->findByPK($id);
		
		if(isset($_POST['Webshop']))
		{
			$model->attributes=$_POST['Webshop'];
			
			if($model->save()) {
				$this->redirect(Yii::app()->homeUrl.'admin/webshop/list/');
			}
		}
		
		$this->render('webshop_form',array(
			'model'=>$model,
		));
	}
	
	
	public function actionDelete($id)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$model = Webshop::model()->findByPK($id);
		$model->delete();
		$this->redirect(Yii::app()->homeUrl.'admin/webshop/list/');
	}
	
	public function actionCategories()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Manage shop categories');
		
		$model = WebshopCategory::model()->findAll();
		
		if (isset($_GET['id'])) {
			$id = $_GET['id'];
			$form = WebshopCategory::model()->findByPK($id);
		}
		else {
			$form = new WebshopCategory;
		}
		
		if (isset($_POST['WebshopCategory']))
		{
			$form->attributes = $_POST['WebshopCategory'];
			
			if($form->save()) {
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Categories updated!').'</div>');
				$this->redirect(Yii::app()->homeUrl.'admin/webshop/categories');
			}
		}
		
		$this->render('webshop_categories',array(
			'model'=>$model,
			'form'=>$form,
		));
	}
	
	public function actionCDelete($id)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$model = WebshopCategory::model()->findByPK($id);
		$model->delete();
		$this->redirect(Yii::app()->homeUrl.'admin/webshop/categories');
	}
	
	function parse($id)
	{
		$xml = 'http://www.aiondatabase.com/xml/en_US/items/xmls/'.$id.'.xml'; // EXAMPLE: http://www.aiondatabase.com/xml/en_US/items/xmls/182207845.xml
		$xmlstr = @file_get_contents($xml);
		if ($xmlstr === FALSE) die('Error connect to xml: '.$xml);
		$xml = new SimpleXMLElement($xmlstr);
		if ($xml === FALSE) die('Error parse xml: '.$xml);
		
		foreach ($xml->xpath('//aionitem') as $item ) {
			$info['name'] = $item->name;
			$info['level'] = $item->level;
		}
		
		return $info;
	}
	
	
	public function actionMembership()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Manage privileges');
		
		$model = Membership::model()->findAll();
		
		if (isset($_GET['id'])) {
			$id = $_GET['id'];
			$form = Membership::model()->findByPK($id);
		}
		else {
			$form = new Membership;
		}
		
		if (isset($_POST['Membership']))
		{
			$form->attributes = $_POST['Membership'];
			
			if($form->save()) {
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Privileges updated!').'</div>');
				$this->redirect(Yii::app()->homeUrl.'admin/webshop/membership');
			}
		}
		
		$this->render('membership_form',array(
			'model'=>$model,
			'form'=>$form,
		));
	}
	
	
	public function actionMDelete ($id)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$model = Membership::model()->findByPK($id);
		$model->delete();
		$this->redirect(Yii::app()->homeUrl.'admin/webshop/membership/');
	}
}