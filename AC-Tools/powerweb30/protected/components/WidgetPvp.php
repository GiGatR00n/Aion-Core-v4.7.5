<?php

class WidgetPvp extends CWidget
{
	public function run()
	{
		if (Yii::app()->getController()->id == 'news' && Yii::app()->getController()->action->id == 'index') {
			$criteria=new CDbCriteria;
			$criteria->select = 'name, exp, gender, race, player_class, online';
			$criteria->condition = 'exp > 100';
			$criteria->order = 'weekly_kill DESC, all_kill DESC, ap DESC, gp DESC';
			$criteria->join = 'INNER JOIN '.Config::db('ls').'.account_data ON (account_data.id=t.account_id AND account_data.access_level < '.Config::get('hide_top').')';
			$criteria->limit = 10;
			$model = Players::model()->with('abyssRank')->findAll($criteria);
			
			$this->render('widgetPvp', array('model'=>$model));
		}
    }
}