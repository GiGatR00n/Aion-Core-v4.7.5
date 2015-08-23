<?php

class WidgetCategories extends CWidget
{
	public function run()
	{
		$criteria=new CDbCriteria;
		$criteria->select = 'name, alt_name, title';
		$criteria->order = 'name';
		$model = NewsCategory::model()->findAll($criteria);
		
		$this->render('widgetCategories', array(
			'model'=>$model
		));
    }
}