<?php

class UserbarForm extends CFormModel {
	public $player_id;
	public $name;
	public $legion;
	public $class_race;
	public $level;
	public $kills;
	public $stats;
	public $shadow;
	public $fon;

	public function rules() {
		//return array(array('player_id, color', 'background', 'integerOnly'=>true));
		return array(array('player_id, name, legion, class_race, level, kills, stats, shadow, fon', 'required'));
		return array(array('player_id, fon', 'integerOnly'=>true));
		return array(array('name, legion, class_race, level, kills, stats, shadow', 'length', 'max'=>7));
	}

	public function safeAttributes() {
		//return array('mobId, item_id',);
	}

}