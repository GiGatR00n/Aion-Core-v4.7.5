<?php

/**
 * This is the model class for table "aion_gs.siege_locations".
 *
 * The followings are the available columns in table 'aion_gs.siege_locations':
 * @property integer $id
 * @property string $race
 * @property integer $legion_id
 */
class SiegeLocations extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return SiegeLocations the static model class
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
		return Config::db('gs').'.siege_locations';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('id, race, legion_id', 'required'),
			array('id, legion_id', 'numerical', 'integerOnly'=>true),
			array('race', 'length', 'max'=>9),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('id, race, legion_id', 'safe', 'on'=>'search'),
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
			'id' => 'ID',
			'race' => 'Race',
			'legion_id' => 'Legion',
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

		$criteria->compare('id',$this->id);
		$criteria->compare('race',$this->race,true);
		$criteria->compare('legion_id',$this->legion_id);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}