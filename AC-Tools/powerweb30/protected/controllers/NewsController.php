<?php







class NewsController extends Controller
{
	public $layout='//content';
	
	protected function performAjaxValidation($model) {
		if(isset($_POST['ajax']) && $_POST['ajax']==='user-form') {
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}
	}

	
	public function actionIndex()
	{
		$this->pageTitle = Yii::t('title', 'News');
		Yii::app()->clientScript->registerMetaTag('Powered By PowerWeb '.Yii::app()->params->version, 'generator');
		
		$criteria=new CDbCriteria;
		$criteria->select = 'news_id, user_id, title, category_id, short_story, date';
		$criteria->order = 'news_id DESC';
		
		$pages=new CPagination(News::model()->count($criteria));
		$pages->pageSize=Config::get('page_news');
		$pages->applyLimit($criteria);
		
		$model = News::model()->with('category', 'author')->findAll($criteria);
		
		$this->render('news_short', array(
			'model' => $model,
			'pages' => $pages,
		));
	}
	
	
	public function actionView($id)
	{
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->condition = 'news_id = '.$id;
		$model = News::model()->find($criteria);
		
		if ($model) {
			$this->pageTitle = $model->title;
			Yii::app()->clientScript->registerMetaTag($model->description, 'description');
			Yii::app()->clientScript->registerMetaTag($model->keywords, 'keywords');
			
			$this->render('news_full', array(
				'model' => $model,
			));
		}
		else $this->redirect(Yii::app()->homeUrl);
	}
	
	
	public function actionAdd()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_editor')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Add a news article');
		
		$model=new News;
		
		$this->performAjaxValidation($model);
		
		if(isset($_POST['News']))
		{
			$model->attributes = $_POST['News'];
			$model->user_id = Yii::app()->user->id;
			
			if($model->save())		
				$this->redirect(Yii::app()->homeUrl.'news/'.$model->news_id);
		}
		
		$this->render('news_form',array(
			'model'=>$model,
		));
	}
	
	public function actionEdit($id)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_editor')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Edit news articles');
		
		$model = News::model()->findByPK($id);
		
		$this->performAjaxValidation($model);
		
		if(isset($_POST['News']))
		{
			$model->attributes=$_POST['News'];
			
			if($model->save())		
				$this->redirect(Yii::app()->homeUrl.'news/'.$model->news_id);
		}
		
		$this->render('news_form',array(
			'model'=>$model,
		));
	}
	
	
	public function actionDelete($id)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_editor')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$model = News::model()->findByPK($id);
		$model->delete();
		$this->redirect(Yii::app()->homeUrl);
	}
	
	
	public function actionCategory($name)
	{
		$category = NewsCategory::model()->find('alt_name = "'.$name.'"');
		
		if ($category)
		{
			$criteria=new CDbCriteria;
			$criteria->select = '*';
			$criteria->condition = 't.category_id = '.$category->category_id;
			
			$pages=new CPagination(News::model()->count($criteria));
			$pages->pageSize=Config::get('page_news');
			$pages->applyLimit($criteria);
			
			$model = News::model()->with('author', 'category')->findAll($criteria);
		
			$this->pageTitle = $category->title;
			Yii::app()->clientScript->registerMetaTag($category->description, 'description');
			Yii::app()->clientScript->registerMetaTag($category->keywords, 'keywords');
			
			$this->render('news_short', array(
				'model' => $model,
				'pages' => $pages,
			));
		}
		else $this->redirect(Yii::app()->homeUrl);
	}
	
	
	public function actionList()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_editor')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'News articles');
		
		$criteria=new CDbCriteria;
		$criteria->select = 'news_id, user_id, title, category_id, short_story, date';
		$criteria->order = 'news_id DESC';
		
		$pages=new CPagination(News::model()->count($criteria));
		$pages->pageSize=25;
		$pages->applyLimit($criteria);
		
		$model = News::model()->with('category', 'author')->findAll($criteria);
		
		$this->render('news_list', array(
			'model' => $model,
			'pages' => $pages,
		));
	}
	
	
	public function actionCategories()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Manage news categories');
		
		$model = NewsCategory::model()->findAll();
		
		if (isset($_GET['id'])) {
			$id = $_GET['id'];
			$form = NewsCategory::model()->findByPK($id);
		}
		else {
			$form = new NewsCategory;
		}
		
		$this->performAjaxValidation($form);
		
		if(isset($_POST['NewsCategory']))
		{
			$form->attributes = $_POST['NewsCategory'];
			
			if($form->save()) {
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'News articles edited!').'</div>');
				$this->redirect(Yii::app()->homeUrl.'admin/news/categories/');
			}
		}
		
		$this->render('category_form',array(
			'model'=>$model,
			'form'=>$form,
		));
	}
	
	
	public function actionCDelete ($id)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$model = NewsCategory::model()->findByPK($id);
		$model->delete();
		$this->redirect(Yii::app()->homeUrl.'news/categories');
	}
}