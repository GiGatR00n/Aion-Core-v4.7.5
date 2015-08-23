<?php

class Mail extends CActiveRecord
{
	public $player_name;
	public $item_id;
	public $item_count;
	
	public function getDbConnection(){
		return  Yii ::app()->gs;
	}
	
	/**
	 * Returns the static model of the specified AR class.
	 * @return Mail the static model class
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
		return 'mail';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('mail_unique_id, mail_recipient_id, sender_name, mail_title, mail_message, attached_item_id, attached_kinah_count, recievedTime', 'required'),
			array('mail_unique_id, mail_recipient_id, unread, attached_item_id, express', 'numerical', 'integerOnly'=>true),
			array('sender_name', 'length', 'max'=>35),
			array('mail_title, attached_kinah_count', 'length', 'max'=>20),
			array('mail_message', 'length', 'max'=>1000),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('mail_unique_id, mail_recipient_id, sender_name, mail_title, mail_message, unread, attached_item_id, attached_kinah_count, express, recievedTime', 'safe', 'on'=>'search'),
			
			// Отправка почты
			array('player_name, item_id, item_count', 'safe'),
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
			'mail_unique_id' => 'Unique message ID',
			'mail_recipient_id' => 'Recipient ID',
			'sender_name' => 'Sender Name',
			'mail_title' => 'Title',
			'mail_message' => 'Message',
			'unread' => 'Unread',
			'attached_item_id' => 'attached_item_id',
			'attached_kinah_count' => 'Attached Kinah Count',
			'express' => 'Express',
			'recievedTime' => 'Received Time',
			'player_name' => 'Player name',
			'item_id' => 'Item ID',
			'item_count' => 'Kinah to attach<br/> <span style="font-size: 10px;">(leave blank if none)</span>',
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

		$criteria->compare('mail_unique_id',$this->mail_unique_id);
		$criteria->compare('mail_recipient_id',$this->mail_recipient_id);
		$criteria->compare('sender_name',$this->sender_name,true);
		$criteria->compare('mail_title',$this->mail_title,true);
		$criteria->compare('mail_message',$this->mail_message,true);
		$criteria->compare('unread',$this->unread);
		$criteria->compare('attached_item_id',$this->attached_item_id);
		$criteria->compare('attached_kinah_count',$this->attached_kinah_count,true);
		$criteria->compare('express',$this->express);
		$criteria->compare('recievedTime',$this->recievedTime,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}