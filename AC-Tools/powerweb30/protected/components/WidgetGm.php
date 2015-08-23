<?php

class WidgetGm extends CWidget {

	public function run()
	{
        $criteria=new CDbCriteria;
		$criteria->select = '*';
		$criteria->condition='access_level > 0';
		
		$model = AccountData::model()->with(array(
			'players'=>array(
				'select'=> 'name, race',
				'joinType'=>'INNER JOIN',
				'condition'=>'online = 1',
			),
		))->findAll($criteria);
		
        $this->render('widgetGm',array('model'=>$model));
    }
}