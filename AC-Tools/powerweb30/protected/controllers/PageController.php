<?php







class PageController extends Controller
{
	public $layout='//content';
	
	protected function performAjaxValidation($model) {
		if(isset($_POST['ajax']) && $_POST['ajax']==='user-form') {
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}
	}
	
	
	public function actionPage($name)
	{
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->condition = 'name = "'.$name.'"';
		$model = Pages::model()->find($criteria);
		
		if ($model) {
			$this->pageTitle = $model->title;
			Yii::app()->clientScript->registerMetaTag($model->description, 'description');
			Yii::app()->clientScript->registerMetaTag($model->keywords, 'keywords');
			
			$this->render('page_view', array(
				'model' => $model,
			));
		}
		else $this->redirect(Yii::app()->homeUrl);
	}
	
	
	public function actionAdd()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Add a page');
		
		$model = new Pages;

		$this->performAjaxValidation($model);

		if(isset($_POST['Pages']))
		{
			$model->attributes=$_POST['Pages'];
			
			if($model->save())				
				$this->redirect(array('page','name'=>$model->name));
		}

		$this->render('page_form',array(
			'model'=>$model,
		));
	}
	
	
	public function actionEdit($id)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Edit a page');
			
		$model = Pages::model()->findByPK($id);
		
		$this->performAjaxValidation($model);
		
		if(isset($_POST['Pages']))
		{
			$model->attributes=$_POST['Pages'];
			
			if($model->save())		
				$this->redirect(Yii::app()->homeUrl.'page/'.$model->name);
		}
		
		$this->render('page_form',array(
			'model'=>$model,
		));
	}
	
	
	public function actionDelete($id)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$model = Pages::model()->findByPK($id);
		$model->delete();
		$this->redirect(Yii::app()->homeUrl);
	}
	
	
	public function actionList()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Page list');
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->order = 'id DESC';
		
		$pages=new CPagination(Pages::model()->count($criteria));
		$pages->pageSize=25;
		$pages->applyLimit($criteria);
		
		$model = Pages::model()->findAll($criteria);
		
		$this->render('page_list', array(
			'model' => $model,
			'pages' => $pages,
		));
	}
	
}