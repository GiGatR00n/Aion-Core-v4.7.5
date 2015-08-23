<?php

/**
 * This is the model class for table "transferpoints".
 *
 * The followings are the available columns in table 'transfer_points':
 * @property integer $id
 * @property string $sender
 * @property string $recipient
 * @property integer $sum
 * @property string $date
 */
class LogPoints extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return TransferPoints the static model class
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
		return 'log_points';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('sender, recipient, sum', 'required'),
			array('sum', 'numerical', 'integerOnly'=>true, 'min' => '1'),
			array('sender, recipient', 'length', 'max'=>32),
			array('date', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('id, sender, recipient, sum, date', 'safe', 'on'=>'search'),
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
			'sender' => 'Sender',
			'recipient' => 'Recipient',
			'sum' => 'Total',
			'date' => 'Date',
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
		$criteria->compare('sender',$this->sender,true);
		$criteria->compare('recipient',$this->recipient,true);
		$criteria->compare('sum',$this->sum);
		$criteria->compare('date',$this->date,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}