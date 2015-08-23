<?php

/**
 * This is the model class for table "settings_vote".
 *
 * The followings are the available columns in table 'settings_vote':
 * @property integer $id
 * @property string $aiontop_link
 * @property integer $aiontop_reg
 * @property string $l2top_link
 * @property integer $l2top_reg
 * @property string $mmotop_link
 * @property integer $mmotop_reg
 * @property integer $mmotop_sms
 * @property string $gtop_link
 * @property integer $gtop_reg
 * @property string $gamesites_link
 * @property integer $gamesites_reg
 * @property string $xtremetop_link
 * @property integer $xtremetop_reg
 */
class SettingsVote extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return SettingsVote the static model class
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
		return 'settings_vote';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('aiontop_reg, l2top_reg, mmotop_reg, mmotop_sms, gtop_reg, gamesites_reg, xtremetop_reg', 'numerical', 'integerOnly'=>true),
			array('aiontop_link, l2top_link, mmotop_link, gtop_link, gamesites_link, xtremetop_link', 'length', 'max'=>128),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('id, aiontop_link, aiontop_reg, l2top_link, l2top_reg, mmotop_link, mmotop_reg, mmotop_sms, gtop_link, gtop_reg, gamesites_link, gamesites_reg, xtremetop_link, xtremetop_reg', 'safe', 'on'=>'search'),
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
			'aiontop_link' => 'Aiontop Link',
			'aiontop_reg' => 'Aiontop Reg',
			'l2top_link' => 'L2top Link',
			'l2top_reg' => 'L2top Reg',
			'mmotop_link' => 'Mmotop Link',
			'mmotop_reg' => 'Mmotop Reg',
			'mmotop_sms' => 'Mmotop Sms',
			'gtop_link' => 'Gtop Link',
			'gtop_reg' => 'Gtop Reg',
			'gamesites_link' => 'Gamesites Link',
			'gamesites_reg' => 'Gamesites Reg',
			'xtremetop_link' => 'Xtremetop Link',
			'xtremetop_reg' => 'Xtremetop Reg',
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
		$criteria->compare('aiontop_link',$this->aiontop_link,true);
		$criteria->compare('aiontop_reg',$this->aiontop_reg);
		$criteria->compare('l2top_link',$this->l2top_link,true);
		$criteria->compare('l2top_reg',$this->l2top_reg);
		$criteria->compare('mmotop_link',$this->mmotop_link,true);
		$criteria->compare('mmotop_reg',$this->mmotop_reg);
		$criteria->compare('mmotop_sms',$this->mmotop_sms);
		$criteria->compare('gtop_link',$this->gtop_link,true);
		$criteria->compare('gtop_reg',$this->gtop_reg);
		$criteria->compare('gamesites_link',$this->gamesites_link,true);
		$criteria->compare('gamesites_reg',$this->gamesites_reg);
		$criteria->compare('xtremetop_link',$this->xtremetop_link,true);
		$criteria->compare('xtremetop_reg',$this->xtremetop_reg);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}