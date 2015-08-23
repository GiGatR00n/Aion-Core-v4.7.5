<?php

class WidgetPages extends CWidget
{
	public function run()
	{
		$criteria=new CDbCriteria;
		$criteria->select = 'name, title';
		$criteria->order = 'name';
		$model = Pages::model()->findAll($criteria);
		
		$this->render('widgetPages', array(
			'model'=>$model
		));
    }
}