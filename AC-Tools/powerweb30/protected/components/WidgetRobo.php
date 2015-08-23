<?php

class WidgetRobo extends CWidget
{
	public function run()
	{
		$model = SettingsPay::model()->find();
		
		if (isset($model->mrh_login))
		{
			$out_summ = NULL;
			$inv_id = "0";
			$billing_name = Yii::app()->user->name;
			$crc = md5("$model->mrh_login:$out_summ:$inv_id:$model->mrh_pass1:Shp_account=$billing_name");
			
			$this->render('widgetRobo', array(
				'model' => $model,
				'crc' => $crc,
			));
		}
    }
}