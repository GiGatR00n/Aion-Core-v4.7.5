<?php

class Droplist extends CActiveRecord
{
	/*
	* Переменные для запроса, если различаются имена столбцов
	
	public $item_id;
	public $item_count;
	public $broker_race;
	public $expire_time;
	public $isSold;
	*/
	
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
	
	/*
	* Название таблицы
	*/
	public function tableName()
	{
		return Config::db('gs').'.droplist';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('mobId, item_id, min, max', 'numerical', 'integerOnly'=>true),
			array('chance', 'numerical'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('Id, mobId, item_id, min, max, chance', 'safe', 'on'=>'search'),
		);
	}

	/**
	 * @return array relational rules.
	 */
	public function relations()
	{
		// NOTE: you may need to adjust the relation name and the related
		// class name for the relations automatically generated below.
		return array(
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'Id' => 'ID',
			'mobId' => 'Mob',
			'item_id' => 'Item',
			'min' => 'Min',
			'max' => 'Max',
			'chance' => 'Chance',
		);
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 * @return CActiveDataProvider the data provider that can return the models based on the search/filter conditions.
	 */
	public function search()
	{
		// Warning: Please modify the following code to remove attributes that
		// should not be searched.

		$criteria=new CDbCriteria;

		$criteria->compare('Id',$this->Id);
		$criteria->compare('mobId',$this->mobId);
		$criteria->compare('item_id',$this->item_id);
		$criteria->compare('min',$this->min);
		$criteria->compare('max',$this->max);
		$criteria->compare('chance',$this->chance);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}