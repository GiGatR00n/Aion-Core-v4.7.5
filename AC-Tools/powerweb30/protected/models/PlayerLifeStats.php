<?php

/**
 * This is the model class for table "aion_gs.player_life_stats".
 *
 * The followings are the available columns in table 'aion_gs.player_life_stats':
 * @property integer $player_id
 * @property integer $hp
 * @property integer $mp
 * @property integer $fp
 */
class PlayerLifeStats extends CActiveRecord
{
	public function getDbConnection(){
		return  Yii ::app()->gs;
	}
	/**
	 * Returns the static model of the specified AR class.
	 * @return PlayerLifeStats the static model class
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
		return 'player_life_stats';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('player_id', 'required'),
			array('player_id, hp, mp, fp', 'numerical', 'integerOnly'=>true),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('player_id, hp, mp, fp', 'safe', 'on'=>'search'),
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
			'player_id' => 'Player',
			'hp' => 'HP',
			'mp' => 'MP',
			'fp' => 'FP',
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

		$criteria->compare('player_id',$this->player_id);
		$criteria->compare('hp',$this->hp);
		$criteria->compare('mp',$this->mp);
		$criteria->compare('fp',$this->fp);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}