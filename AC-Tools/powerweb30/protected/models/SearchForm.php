<?php







class SearchForm extends CFormModel
{
	public $mobId;
	public $item_id;
	public $name;
	public $captcha;

	public function rules()
	{
		return array(
			array('captcha', 'required'),
			array('captcha', 'captcha', 'allowEmpty'=>!extension_loaded('gd')),
			
			array('mobId, item_id', 'numerical', 'integerOnly'=>true, 'on'=>'droplist'),
			array('mobId', 'length', 'is'=>6, 'on'=>'droplist'),
			array('item_id', 'length', 'is'=>9, 'on'=>'droplist'),
			
			array('name', 'required', 'on'=>'player'),
			array('name', 'length', 'min'=>3, 'max'=>45, 'on'=>'player'),
			array('name', 'match', 'pattern'=>'/^[A-Za-z0-9][\w]+$/', 'on'=>'player'),
		);
	}
	
	public function attributeLabels()
	{
		return array(
			'mobId' => 'Mob ID',
			'item_id' => 'Item ID',
			'name' => 'Character name',
			'captcha' => 'Captcha',
		);
	}
}