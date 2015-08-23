<?php







class TopController extends Controller
{
	public $layout='//content';
	
	
	public function actionIndex()
	{
		$this->pageTitle = Yii::t('title', 'Top players');
		
		$criteria=new CDbCriteria;
		$criteria->select = 'name, exp, gender, race, player_class, online';
		$criteria->condition = 'exp > 0';
		$criteria->order = 'exp DESC, ap DESC';
		$criteria->join = 'INNER JOIN '.Config::db('ls').'.account_data ON (account_data.id=t.account_id AND account_data.access_level < '.Config::get('hide_top').')';
		
		$pages=new CPagination(Players::model()->count($criteria));
		$pages->pageSize = Config::get('page_top');
		$pages->applyLimit($criteria);
		
		$players = Players::model()->with('abyssRank')->findAll($criteria);
		
		$this->render('top', array(
			'players' => $players,
			'pages' => $pages,
		));
	}
	
	
	public function actionAbyss()
	{
		$this->pageTitle = Yii::t('title', 'Top Abyss players');
		
		$model = new Players('topSearch');
		
		if (isset($_GET['Players'])) {
			$model->attributes=$_GET['Players'];
		}
		
		$this->render('abyss', array(
			'model' => $model,
		));
	}
	
	
	public function actionLegion() {
		$this->pageTitle = Yii::t('title', 'Top legion');
		
		$criteria=new CDbCriteria;
		$criteria->select = 'id, name, contribution_points, level';
		$criteria->condition = 'contribution_points > 0';
		$criteria->order = 'contribution_points DESC';
		
		$pages=new CPagination(Legions::model()->count($criteria));
		$pages->pageSize = Config::get('page_top');
		$pages->applyLimit($criteria);
		
		$model = Legions::model()->with('membersCount', 'legat')->findAll($criteria);
		
		$this->render('legion', array(
			'model' => $model,
			'pages' => $pages,
		));
		
	}
	
	public function actionOnline()
	{
		$this->pageTitle = Yii::t('title', 'Players online');
		
		$criteria=new CDbCriteria;
		$criteria->select = 'name, account_id, exp, gender, race, player_class, world_id, show_location';
		$criteria->condition = 'online = 1';
		$criteria->order = 'exp DESC, ap DESC';
		$criteria->join = 'INNER JOIN '.Config::db('ls').'.account_data ON (account_data.id=t.account_id AND account_data.access_level < '.Config::get('hide_top').')';
		
		$pages=new CPagination(Players::model()->count($criteria));
		$pages->pageSize = Config::get('page_top');
		$pages->applyLimit($criteria);
		
		$model = Players::model()->with('abyssRank')->findAll($criteria);
		
		$this->render('online', array(
			'model' => $model,
			'pages' => $pages,
		));
	}

}