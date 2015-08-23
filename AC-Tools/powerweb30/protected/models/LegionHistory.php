<?php

/**
 * This is the model class for table "aion_gs.legion_history".
 *
 * The followings are the available columns in table 'aion_gs.legion_history':
 * @property integer $id
 * @property integer $legion_id
 * @property string $date
 * @property string $history_type
 * @property string $name
 */
class LegionHistory extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return LegionHistory the static model class
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
		return Config::db('gs').'.legion_history';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('legion_id, date, history_type, name', 'required'),
			array('legion_id', 'numerical', 'integerOnly'=>true),
			array('history_type', 'length', 'max'=>15),
			array('name', 'length', 'max'=>16),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('id, legion_id, date, history_type, name', 'safe', 'on'=>'search'),
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
			'legion_id' => 'Legion',
			'date' => 'Date',
			'history_type' => 'History Type',
			'name' => 'Name',
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
		$criteria->compare('legion_id',$this->legion_id);
		$criteria->compare('date',$this->date,true);
		$criteria->compare('history_type',$this->history_type,true);
		$criteria->compare('name',$this->name,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}