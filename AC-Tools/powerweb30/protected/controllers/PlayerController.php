<?php







class PlayerController extends Controller
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


	public function actionIndex($name)
	{
		$this->pageTitle = Yii::t('title', 'Player info: ').' '.$name;
		
		$criteria=new CDbCriteria;
		$criteria->select = 'id, name, account_id, exp, gender, race, player_class, online, world_id, title_id, creation_date, last_online, show_location, show_inventory';
		$criteria->condition = 'name = "'.$name.'"';
		$player = Players::model()->with('abyssRank', 'lifeStat')->find($criteria);
		
		$criteria=new CDbCriteria;
		$criteria->select = 'item_id, slot';
		$criteria->condition = 'item_owner = '.$player->id.' AND is_equiped = 1';
		$inventory = Inventory::model()->findAll($criteria);
		
		$this->render('player', array(
			'player' => $player,
			'inventory' => $inventory,
		));
	}
	
	
	public function actionSearch()
	{
		$this->pageTitle = Yii::t('title', 'Player search');
		
		$form = new SearchForm();
		$form->scenario = 'player';
		$model = null;
		
		$this->performAjaxValidation($form);
		
		if(isset($_POST['SearchForm']))
		{
			$form->attributes = $_POST['SearchForm'];
			
			if ($form->validate())
			{
				$criteria=new CDbCriteria;
				$criteria->select = 'name, exp, gender, race, player_class, online';
				$criteria->condition = 'exp > 0 AND name LIKE "%'.$form->name.'%"';
				$criteria->order = 'exp DESC, ap DESC';
				$criteria->limit = 25;
				$model = Players::model()->with('abyssRank')->findAll($criteria);
			}
		}
		
		$this->render('player_search', array(
			'form' => $form,
			'model' => $model,
		));
	}
	
	
	public function actionList()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Character list');
		
		$model=new Players('search');
		if(isset($_GET['Players']))
			$model->attributes=$_GET['Players'];
		$this->render('player_list',array(
			'model'=>$model,
		));
	}
	
	
	public function actionEdit($name)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Edit character');
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->condition = 'name = "'.$name.'"';
		
		$model = Players::model()->find($criteria);
		$model->scenario = 'admin_edit';
		
		if(isset($_POST['Players']))
		{
			$model->attributes = $_POST['Players'];
			
			if ($model->save()) {
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Character editing successful!').'</div>');
				$this->redirect(Yii::app()->homeUrl.'admin/player/'.$model->name.'/');
			}
		}
		
		$this->render('player_form', array(
			'model' => $model,
		));
	}

}