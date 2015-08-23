<?php







class LegionController extends Controller
{
	public $layout='//content';
	
	protected function performAjaxValidation($model) {
		if(isset($_POST['ajax']) && $_POST['ajax']==='user-form') {
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}
	}


	public function actionIndex($name)
	{
		$this->pageTitle = Yii::t('title', 'Legion information - ').' '.$name;
		
		$criteria=new CDbCriteria;
		$criteria->select = 'id, name, contribution_points, level';
		$criteria->condition = 'name = "'.$name.'"';
		$model = Legions::model()->with('membersCount')->find($criteria);
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->condition = 't.id = '.$model->id;
		$players = Legions::model()->with('legionMembers', 'legionHistory')->find($criteria);
		
		$this->render('legion', array(
			'model' => $model,
			'players' => $players,
		));
	}
	
	
	public function actionList()
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Legion list');
		
		$model=new Legions('search');
		if(isset($_GET['Legions']))
			$model->attributes=$_GET['Legions'];
		$this->render('legion_list',array(
			'model'=>$model,
		));
	}
	
	
	public function actionView($name)
	{
		if (Yii::app()->user->isGuest OR Yii::app()->user->access_level < Config::get('access_level_admin')) {
			$this->redirect(Yii::app()->homeUrl);
		}
		
		$this->pageTitle = Yii::t('title', 'Edit legions');
		
		$criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->condition = 'name = "'.$name.'"';
		
		$model = Legions::model()->find($criteria);
		$model->scenario = 'admin_edit';
		
		if(isset($_POST['Legions']))
		{
			$model->attributes = $_POST['Legions'];
			
			if ($model->save()) {
				Yii::app()->user->setFlash('message', '<div class="flash_success">'.Yii::t('main', 'Legions modified!').'</div>');
				$this->redirect(Yii::app()->homeUrl.'admin/legion/'.$model->name.'/');
			}
		}

		$this->render('legion_form', array(
			'model' => $model,
		));
	}


}