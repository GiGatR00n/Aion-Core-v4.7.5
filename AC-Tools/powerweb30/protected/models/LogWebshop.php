<?php

/**
 * This is the model class for table "log_webshop".
 *
 * The followings are the available columns in table 'log_webshop':
 * @property integer $id
 * @property string $player
 * @property integer $item
 * @property integer $amount
 * @property integer $price
 * @property string $date
 */
class LogWebshop extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return LogWebshop the static model class
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
		return 'log_webshop';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('player_id, item, amount, price', 'required'),
			array('item, amount, price', 'numerical', 'integerOnly'=>true),
			array('player_id', 'length', 'max'=>32),
			array('date', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('id, player_id, item, amount, price, date', 'safe', 'on'=>'search'),
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
			'players' => array(self::BELONGS_TO, 'Players', 'player_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'id' => 'ID',
			'player_id' => 'Player',
			'item' => 'Item',
			'amount' => 'Amount',
			'price' => 'Price',
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
		$criteria->compare('player_id',$this->player,true);
		$criteria->compare('item',$this->item);
		$criteria->compare('amount',$this->amount);
		$criteria->compare('price',$this->price);
		$criteria->compare('date',$this->date,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}