<?php

/**
 * This is the model class for table "aion_gs.legion_members".
 *
 * The followings are the available columns in table 'aion_gs.legion_members':
 * @property integer $legion_id
 * @property integer $player_id
 * @property string $nickname
 * @property string $rank
 * @property string $selfintro
 */
class LegionMembers extends CActiveRecord
{
	public $id;
	/**
	 * Returns the static model of the specified AR class.
	 * @return LegionMembers the static model class
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
		return Config::db('gs').'.legion_members';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('legion_id, player_id', 'required'),
			array('legion_id, player_id', 'numerical', 'integerOnly'=>true),
			array('nickname', 'length', 'max'=>16),
			array('rank', 'length', 'max'=>15),
			array('selfintro', 'length', 'max'=>25),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('legion_id, player_id, nickname, rank, selfintro', 'safe', 'on'=>'search'),
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
			'legions' => array(self::HAS_ONE, 'Legions', 'id'),
			'players' => array(self::HAS_ONE, 'Players', 'id'),
			'abyssRank' => array(self::HAS_ONE, 'AbyssRank', 'player_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'legion_id' => 'Legion',
			'player_id' => 'Player',
			'nickname' => 'Nickname',
			'rank' => 'Rank',
			'selfintro' => 'Selfintro',
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

		$criteria->compare('legion_id',$this->legion_id);
		$criteria->compare('player_id',$this->player_id);
		$criteria->compare('nickname',$this->nickname,true);
		$criteria->compare('rank',$this->rank,true);
		$criteria->compare('selfintro',$this->selfintro,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}