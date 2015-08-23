<?php

class WidgetIk extends CWidget
{
	public function run()
	{
		$model = SettingsPay::model()->find();
		
		if (isset($model->ik_shop_id))
		{
			$this->render('widgetIk', array(
				'model' => $model,
			));
		}
    }
}