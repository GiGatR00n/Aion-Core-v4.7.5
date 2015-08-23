<?php

/**
 * This is the model class for table "referals".
 *
 * The followings are the available columns in table 'referals':
 * @property integer $id
 * @property string $master
 * @property string $slave
 * @property string $date
 * @property string $status
 */
class LogReferals extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return Referals the static model class
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
		return Config::db('db').'.log_referals';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('master, master_id, slave, slave_id, date', 'required'),
			array('master, master_id, slave, slave_id', 'length', 'max'=>32),
			array('status', 'length', 'max'=>8),
			array('date', 'safe'),
			
			array('master, master_id, slave, slave_id, date', 'safe', 'on'=>'update'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('id, master, slave, date, status', 'safe', 'on'=>'search'),
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
			'players' => array(self::HAS_ONE, 'Players', 'account_id'),
			'refs' => array(self::HAS_ONE, 'Players', 'account_id'),
			'accountData' => array(self::HAS_ONE, 'Players', 'account_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'id' => 'ID',
			'master' => 'Master',
			'slave' => 'Slave',
			'date' => 'Date',
			'status' => 'Status',
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
		$criteria->compare('master',$this->master,true);
		$criteria->compare('slave',$this->slave,true);
		$criteria->compare('date',$this->date,true);
		$criteria->compare('status',$this->status,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}