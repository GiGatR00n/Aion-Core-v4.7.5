<?php
class Status extends CController {

	// Server status
	function server($ip, $port) {
		$fp = @fsockopen ($ip, $port, $errno, $errstr, 1);               
		if ($fp) return Yii::t('main', 'online');
		else return Yii::t('main', 'offline');
	}
	
	
	// Total online players
	function online() {
		$criteria=new CDbCriteria;
		$criteria->condition='online = 1';
		$online = Players::model()->count($criteria);
		return $online;
	}
	
	// Total online Asmodians
	function online_asmo() {
		$criteria=new CDbCriteria;
		$criteria->condition='online = 1 AND race = "ASMODIANS"';
		$alliance = Players::model()->count($criteria);
		return $alliance;
	}
	
	// Total online Elyoss
	function online_ely() {
		$criteria=new CDbCriteria;
		$criteria->condition='online = 1 AND race = "ELYOS"';
		$alliance = Players::model()->count($criteria);
		return $alliance;
	}
	
	
	// Total accounts
	function accounts() {
		$criteria=new CDbCriteria;
		$model = AccountData::model()->count($criteria);
		return $model;
	}
	
	// Total number of players
	function players() {
		$criteria=new CDbCriteria;
		$model = Players::model()->count($criteria);
		return $model;
	}
	
	// Total number of legions
	function legions() {
		$criteria=new CDbCriteria;
		$model = Legions::model()->count($criteria);
		return $model;
	}
	
	// Total number of legions per race
	function legions_race($race) {
		$criteria=new CDbCriteria;
		$criteria->select = 'player_id';
		$criteria->condition='rank = "BRIGADE_GENERAL"';
		
		return LegionMembers::model()->with(array(
			'players'=>array(
				'select'=> 'id, race',
				'joinType'=>'INNER JOIN',
				'condition'=>'race = "'.$race.'"',
			),
		))->count($criteria);
	}
	
	// Total number of GMs
	function gms() {
		$criteria=new CDbCriteria;
		$criteria->condition='access_level > 1';
		$model = AccountData::model()->count($criteria);
		return $model;
	}
	
	// Total number of news articles
	function news() {
		$criteria=new CDbCriteria;
		$model = News::model()->count($criteria);
		return $model;
	}
	
	
	// Total number of Asmodians
	function asmo() {
		$criteria=new CDbCriteria;
		$criteria->condition='race = "ASMODIANS" OR race = "ASMODIAN"';
		return Players::model()->count($criteria);
	}
	
	// Total number of Elyoss
	function ely() {
		$criteria=new CDbCriteria;
		$criteria->condition='race = "ELYOS"';
		return Players::model()->count($criteria);
	}
	
	// Total of characters per class
	function player_class($class) {
		$criteria=new CDbCriteria;
		$criteria->condition='player_class = "'.$class.'"';
		return Players::model()->count($criteria);
	}
	
	// Total number of characters per level bracket
	function level($level) {
		$criteria=new CDbCriteria;
		if ($level == 10) $criteria->condition = 'exp <= 307558';
		elseif ($level == 20) $criteria->condition = 'exp > 307558 AND exp <= 4820229';
		elseif ($level == 30) $criteria->condition = 'exp > 4820229 AND exp <= 35939440';
		elseif ($level == 35) $criteria->condition = 'exp > 35939440 AND exp <= 89321807';
		elseif ($level == 40) $criteria->condition = 'exp > 89321807 AND exp <= 243343723';
		elseif ($level == 45) $criteria->condition = 'exp > 243343723 AND exp <= 559280864';
		elseif ($level == 50) $criteria->condition = 'exp > 559280864 AND exp <= 1128723910';
		elseif ($level == 55) $criteria->condition = 'exp > 1128723910 AND exp <= 1698166956';
		elseif ($level == 60) $criteria->condition = 'exp > 1698166956 AND exp <= 2826890866';
		elseif ($level == 65) $criteria->condition = 'exp > 2826890866';
		else return 0;
		return Players::model()->count($criteria);
	}
}