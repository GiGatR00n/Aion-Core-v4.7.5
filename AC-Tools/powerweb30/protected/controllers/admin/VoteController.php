<?php







class VoteController extends Controller
{
	public $layout='//content';
	
	
	public function actionIndex($name = NULL)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		if ($name == NULL) {
			$this->redirect(Yii::app()->homeUrl.'admin/');
		}
		
		$this->pageTitle = Yii::t('title', 'Server voting');
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->order = 'date DESC';
		
		if ($name == 'aiontop') {
			$pages=new CPagination(LogVoteAiontop::model()->count($criteria));
			$pages->pageSize=50;
			$pages->applyLimit($criteria);
			$model = LogVoteAiontop::model()->findAll($criteria);
			if (isset($_POST['Clean'])) {
				LogVoteAiontop::model()->deleteAll('date < "'.$_POST['Clean']['date'].'"');
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Successfully voted!').'</div>');
				$this->refresh();
			}
		}
		elseif ($name == 'l2top') {
			$pages=new CPagination(LogVoteL2top::model()->count($criteria));
			$pages->pageSize=50;
			$pages->applyLimit($criteria);
			$model = LogVoteL2top::model()->findAll($criteria);
			if (isset($_POST['Clean'])) {
				LogVoteL2top::model()->deleteAll('date < "'.$_POST['Clean']['date'].'"');
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Successfully voted!').'</div>');
				$this->refresh();
			}
		}
		elseif ($name == 'mmotop') {
			$pages=new CPagination(LogVoteMmotop::model()->count($criteria));
			$pages->pageSize=50;
			$pages->applyLimit($criteria);
			$model = LogVoteMmotop::model()->findAll($criteria);
			if (isset($_POST['Clean'])) {
				LogVoteMmotop::model()->deleteAll('date < "'.$_POST['Clean']['date'].'"');
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Successfully voted!').'</div>');
				$this->refresh();
			}
		}
		elseif ($name == 'gtop') {
			$pages=new CPagination(LogVoteGtop100::model()->count($criteria));
			$pages->pageSize=50;
			$pages->applyLimit($criteria);
			$model = LogVoteGtop100::model()->findAll($criteria);
			if (isset($_POST['Clean'])) {
				LogVoteGtop100::model()->deleteAll('date < "'.$_POST['Clean']['date'].'"');
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Successfully voted!').'</div>');
				$this->refresh();
			}
		}
		elseif ($name == 'gamesites') {
			$pages=new CPagination(LogVoteGamesites200::model()->count($criteria));
			$pages->pageSize=50;
			$pages->applyLimit($criteria);
			$model = LogVoteGamesites200::model()->findAll($criteria);
			if (isset($_POST['Clean'])) {
				LogVoteGamesites200::model()->deleteAll('date < "'.$_POST['Clean']['date'].'"');
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Successfully voted!').'</div>');
				$this->refresh();
			}
		}
		elseif ($name == 'xtremetop') {
			$pages=new CPagination(LogVoteXtremetop::model()->count($criteria));
			$pages->pageSize=50;
			$pages->applyLimit($criteria);
			$model = LogVoteXtremetop::model()->findAll($criteria);
			if (isset($_POST['Clean'])) {
				LogVoteXtremetop::model()->deleteAll('date < "'.$_POST['Clean']['date'].'"');
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Successfully voted!').'</div>');
				$this->refresh();
			}
		}
		
		$this->render('/admin/log_vote', array(
			'model' => $model,
			'pages' => $pages,
		));
	}
	
	
	public function actionConfig()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Vote settings');
		
		$model = SettingsVote::model()->find();
		
		if (isset($_POST['SettingsVote']))
		{
			$model->attributes = $_POST['SettingsVote'];
			
			if ($model->save())
			{
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('settings', 'Voting settings modified!').'</div>');
				$this->refresh();
			}
		}

		$this->render('/admin/vote_config', array(
			'model' => $model,
		));
	}
	
	
	public function actionCheckMmotop()
	{
		$config = SettingsVote::model()->find();
		if (!$config->mmotop_link) $this->redirect(Yii::app()->homeUrl.'admin/');
		$link = file($config->mmotop_link);
		$price_reg = $config->mmotop_reg;
		$price_sms = $config->mmotop_sms;
		
		$model = array();
		
		foreach ($link as $row)
		{
			$data = explode("\t", $row);
			$check = LogVoteMmotop::model()->count('id = '.$data[0]);
			
			if ($check == 0)
			{
				$log = new LogVoteMmotop;
				$log->id = $data[0];
				$log->login = $data[3];
				$log->ip = $data[2];
				$log->type = $data[4];
				$log->date = $data[1];
				$log->status = 'unpaid';
				$log->save();
				
				$model[] = array('id' => $data[0], 'login' => $data[3], 'ip' => $data[2], 'type' => $data[4], 'date' => $data[1], 'message' => 'Mmotop vote created!');
			}
		}
		
		$unpaid = LogVoteMmotop::model()->findAll('status = "unpaid"');
		
		foreach ($unpaid as $data)
		{
			$account = AccountData::model()->find('name = "'.$data->login.'"');
			
			if ($account)
			{
				if($data->type == 1) {
					$account[Yii::app()->params->money] = $account[Yii::app()->params->money] + $price_reg;
					$account->update();
				}
				else {
					$account[Yii::app()->params->money] = $account[Yii::app()->params->money] + $price_sms;
					$account->update();
				}
				
				$status = LogVoteMmotop::model()->find('status = "unpaid" AND id = '.$data['id']);
				$status->status = 'complete';
				$status->update();
				
				$model[] = array( 'id' => $data['id'], 'login' => $data['login'], 'type' => $data['type'], 'date' => $data['date'], 'message' => 'Voting status updated!');
			}
			else {
				$model[] = array( 'id' => $data['id'], 'login' => $data['login'], 'type' => $data['type'], 'date' => $data['date'], 'message' => 'Account not found.');
			}
		}
		
		$this->render('/admin/vote_check', array(
			'model' => $model,
		));
	}
	
	
	public function actionCheckAiontop()
	{
		$config = SettingsVote::model()->find();
		if (!$config->aiontop_link) $this->redirect(Yii::app()->homeUrl.'admin/');
		$price_reg = $config->aiontop_reg;
		$link = str_replace('<meta http-equiv="content-type" content="text/html; charset=windows-1251"></head><body>', '', file($config->aiontop_link));
		array_shift($link);
		array_pop($link);
		
		$model = array();
		
		foreach ($link as $row)
		{
			$data = explode(" ", $row);
			
			$date = $data[0].' '.$data[1];
			$login = iconv('cp1251', 'utf-8', trim($data[2]));
			
			$check = LogVoteAiontop::model()->count('date = "'.$date.'" AND login = "'.$login.'"');
			
			if ($check == 0)
			{
				$log = new LogVoteAiontop;
				$log->login = $login;
				$log->type = 1;
				$log->date = $date;
				$log->status = 'unpaid';
				$log->save();
				
				$model[] = array('id' => $log->id, 'login' => $log->login, 'type' => $log->type, 'date' => $log->date, 'message' => 'AionTop vote created!');
			}
		}
		
		$unpaid = LogVoteAiontop::model()->findAll('status = "unpaid"');
		
		foreach ($unpaid as $data)
		{
			$account = AccountData::model()->find('name = "'.$data['login'].'"');
			
			if ($account)
			{
				$account[Yii::app()->params->money] = $account[Yii::app()->params->money] + $price_reg;
				$account->update();
				
				$status = LogVoteAiontop::model()->find('status = "unpaid" AND id = '.$data->id);
				$status->status = 'complete';
				$status->update();
				
				$model[] = array( 'id' => $data['id'], 'login' => $data['login'], 'type' => $data['type'], 'date' => $data['date'], 'message' => 'Voting status updated!');
			}
			else {
				$model[] = array( 'id' => $data['id'], 'login' => $data['login'], 'type' => $data['type'], 'date' => $data['date'], 'message' => 'Account not found.');
			}
		}
		
		$this->render('/admin/vote_check', array(
			'model' => $model,
		));
	}
	
	
	public function actionCheckL2top()
	{
		$config = SettingsVote::model()->find();
		if (!$config->l2top_link) $this->redirect(Yii::app()->homeUrl.'admin/');
		$price_reg = $config->l2top_reg;
		$link = file($config->l2top_link);
		array_shift($link);
		array_shift($link);
		array_pop($link);
		
		$model = array();
		
		foreach ($link as $row)
		{
			$data = explode("\t", $row);
			$check = LogVoteL2top::model()->count('date = "'.$data[0].'" AND login = "'.iconv("cp1251", "utf-8", trim($data[1])).'"');
			
			if ($check == 0)
			{
				$log = new LogVoteL2top;
				$log->login = iconv("cp1251", "utf-8", trim($data[1]));
				$log->type = 1;
				$log->date = $data[0];
				$log->status = 'unpaid';
				$log->save();
				
				$model[] = array('id' => $log->id, 'login' => $log->login, 'type' => $log->type, 'date' => $log->date, 'message' => 'L2top vote created!');
			}
		}
		
		$unpaid = LogVoteL2top::model()->findAll('status = "unpaid"');
		
		foreach ($unpaid as $data)
		{
			$account = AccountData::model()->find('name = "'.$data['login'].'"');
			
			if ($account)
			{
				$account[Yii::app()->params->money] = $account[Yii::app()->params->money] + $price_reg;
				$account->update();
				
				$status = LogVoteL2top::model()->find('status = "unpaid" AND id = '.$data->id);
				$status->status = 'complete';
				$status->update();
				
				$model[] = array( 'id' => $data['id'], 'login' => $data['login'], 'type' => $data['type'], 'date' => $data['date'], 'message' => 'Voting status updated!');
			}
			else {
				$model[] = array( 'id' => $data['id'], 'login' => $data['login'], 'type' => $data['type'], 'date' => $data['date'], 'message' => 'Account not found.');
			}
		}
		
		$this->render('/admin/vote_check', array(
			'model' => $model,
		));
		
	}
	
	public function actionGtop()
	{
		$config = SettingsVote::model()->find();
		
		if (Yii::app()->user->isGuest)
		{
			$this->redirect($config->gtop_link);
		}
		
		if (isset(Yii::app()->request->cookies['pow_vote_gtop']->value))
		{
			$this->redirect(Yii::app()->homeurl);
		}
		else
		{
			$cookie = new CHttpCookie('pow_vote_gtop', Yii::app()->user->id);
			$cookie->expire = time()+60*60*24; 
			Yii::app()->request->cookies['pow_vote_gtop'] = $cookie;
			
			$current = mktime() - 34560;
			$check = LogVoteGtop100::model()->count('date > '.$current.' AND login = "'.Yii::app()->user->name.'"');
			if ($check > 0) {
				$this->redirect(Yii::app()->homeurl);
			}
			
			$criteria = new CDbCriteria;
			$criteria->condition = 'id = '.Yii::app()->user->id;
			$money = AccountData::model()->find($criteria);
			$money[Yii::app()->params->money] = $money[Yii::app()->params->money] + $config->gtop_reg;
			$money->update();
			
			$model = new LogVoteGtop100;
			$model->login = ''.Yii::app()->user->name.'';
			$model->type = 1;
			$model->date = mktime();
			$model->status = 'complete';
			$model->save(false);
			
			$this->redirect($config->gtop_link);
		}
	}
	
	
	public function actionGamesites()
	{
		$config = SettingsVote::model()->find();
		
		if (Yii::app()->user->isGuest)
		{
			$this->redirect($config->gamesites_link);
		}
		
		if (isset(Yii::app()->request->cookies['pow_vote_gamesites']->value))
		{
			$this->redirect(Yii::app()->homeurl);
		}
		else
		{
			$cookie = new CHttpCookie('pow_vote_gamesites', Yii::app()->user->id);
			$cookie->expire = time()+60*60*24; 
			Yii::app()->request->cookies['pow_vote_gamesites'] = $cookie;
			
			$current = mktime() - 34560;
			$check = LogVoteGamesites200::model()->count('date > '.$current.' AND login = "'.Yii::app()->user->name.'"');
			if ($check > 0) {
				$this->redirect(Yii::app()->homeurl);
			}
			
			$criteria = new CDbCriteria;
			$criteria->condition = 'id = '.Yii::app()->user->id;
			$money = AccountData::model()->find($criteria);
			$money[Yii::app()->params->money] = $money[Yii::app()->params->money] + $config->gamesites_reg;
			$money->update();
			
			$model = new LogVoteGamesites200;
			$model->login = ''.Yii::app()->user->name.'';
			$model->type = 1;
			$model->date = mktime();
			$model->status = 'complete';
			$model->save(false);
			
			$this->redirect($config->gamesites_link);
		}
	}
	
	
	public function actionXtremetop()
	{
		$config = SettingsVote::model()->find();
		
		if (Yii::app()->user->isGuest)
		{
			$this->redirect($config->xtremetop_link);
		}
		
		if (isset(Yii::app()->request->cookies['pow_vote_xtremetop']->value))
		{
			$this->redirect(Yii::app()->homeurl);
		}
		else
		{
			$cookie = new CHttpCookie('pow_vote_xtremetop', Yii::app()->user->id);
			$cookie->expire = time()+60*60*24; 
			Yii::app()->request->cookies['pow_vote_xtremetop'] = $cookie;
			
			$current = mktime() - 34560;
			$check = LogVoteXtremetop::model()->count('date > '.$current.' AND login = "'.Yii::app()->user->name.'"');
			if ($check > 0) {
				$this->redirect(Yii::app()->homeurl);
			}
			
			$criteria = new CDbCriteria;
			$criteria->condition = 'id = '.Yii::app()->user->id;
			$money = AccountData::model()->find($criteria);
			$money[Yii::app()->params->money] = $money[Yii::app()->params->money] + $config->xtremetop_reg;
			$money->update();
			
			$model = new LogVoteXtremetop;
			$model->login = ''.Yii::app()->user->name.'';
			$model->type = 1;
			$model->date = mktime();
			$model->status = 'complete';
			$model->save(false);
			
			$this->redirect($config->xtremetop_link);
		}
	}
}