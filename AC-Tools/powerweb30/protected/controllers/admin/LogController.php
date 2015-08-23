<?php







class LogController extends Controller
{
	public $layout='//content';
	
	
	public function actionBilling()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Report - Balance top-ups');
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->order = 'date DESC';
		
		$pages=new CPagination(LogBilling::model()->count($criteria));
		$pages->pageSize = 25;
		$pages->applyLimit($criteria);

		$model = LogBilling::model()->findAll($criteria);

		$this->render('/admin/log_billing', array(
			'model' => $model,
			'pages' => $pages,
		));
	}
	
	
	public function actionWebshop()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Report - Webshop Items');
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->order = 'id DESC';
		
		$pages=new CPagination(LogWebshop::model()->with('players')->count($criteria));
		$pages->pageSize=25;
		$pages->applyLimit($criteria);

		$model = LogWebshop::model()->findAll($criteria);

		$this->render('/admin/log_webshop', array(
			'model' => $model,
			'pages' => $pages,
		));
	}
	
	
	public function actionMembership()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Report - Privileges purchased');
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->order = 'id DESC';
		
		$pages=new CPagination(LogMembership::model()->count($criteria));
		$pages->pageSize=25;
		$pages->applyLimit($criteria);

		$model = LogMembership::model()->findAll($criteria);

		$this->render('/admin/log_membership', array(
			'model' => $model,
			'pages' => $pages,
		));
	}
	
	
	public function actionPoints()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Report - Points transferred');
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->order = 'id DESC';
		
		$pages=new CPagination(LogPoints::model()->count($criteria));
		$pages->pageSize=25;
		$pages->applyLimit($criteria);

		$model = LogPoints::model()->findAll($criteria);

		$this->render('/admin/log_points', array(
			'model' => $model,
			'pages' => $pages,
		));
	}
	
	
	public function actionReferals()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Report - Referrals list');
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->order = 'id DESC';
		
		$pages=new CPagination(LogReferals::model()->count($criteria));
		$pages->pageSize=25;
		$pages->applyLimit($criteria);
		
		$model = LogReferals::model()->findAll($criteria);
		
		$this->render('/admin/log_referals', array(
			'model' => $model,
			'pages' => $pages,
		));
	}
}