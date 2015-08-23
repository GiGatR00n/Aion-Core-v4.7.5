<?php

/**
 * This is the model class for table "log_billing".
 *
 * The followings are the available columns in table 'log_billing':
 * @property integer $note_id
 * @property integer $sum
 * @property string $account
 * @property string $date
 * @property string $status
 */
class LogBilling extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return LogBilling the static model class
	 */
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}

	/**
	 * @return string the associated database table name
	 */
	public function tableName()
	{
		return 'log_billing';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('note_id, sum, account, date', 'required'),
			array('note_id, sum', 'numerical', 'integerOnly'=>true),
			array('account, status', 'length', 'max'=>16),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('note_id, sum, account, date, status', 'safe', 'on'=>'search'),
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
			'note_id' => 'Note',
			'sum' => 'Sum',
			'account' => 'Account',
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

		$criteria->compare('note_id',$this->note_id);
		$criteria->compare('sum',$this->sum);
		$criteria->compare('account',$this->account,true);
		$criteria->compare('date',$this->date,true);
		$criteria->compare('status',$this->status,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}