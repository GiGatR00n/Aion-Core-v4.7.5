<?php

class WidgetLogin extends CWidget {

	public function run()
	{
        $model = new LoginForm;
		
		if(isset($_POST['ajax']) && $_POST['ajax']==='login-form')
		{
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}
		
		if(isset($_POST['LoginForm']))
		{
			$model->attributes=$_POST['LoginForm'];
			if($model->validate() && $model->login()) {
				$this->controller->refresh();
			}
		}
		
        $this->render('widgetLogin', array(
			'model'=>$model
		));
    }
}