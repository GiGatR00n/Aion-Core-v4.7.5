<?php

class PlayerQuests extends CActiveRecord
{
	public function getDbConnection(){
		return  Yii ::app()->gs;
	}
	
	
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
	
	
	public function tableName()
	{
		return Config::db('gs').'.player_quests';
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
			array('player_id', 'numerical', 'integerOnly'=>true),
			array('quest_id, status, quest_vars', 'length', 'max'=>10),
			array('complete_count', 'length', 'max'=>3),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('player_id, quest_id, status, quest_vars, complete_count', 'safe', 'on'=>'search'),
		);
	}

	/**
	 * @return array relational rules.
	 */
	public function relations()
	{
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
			'player_id' => 'Player',
			'quest_id' => 'Quest',
			'status' => 'Status',
			'quest_vars' => 'Quest Vars',
			'complete_count' => 'Complete Count',
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
		$criteria->compare('quest_id',$this->quest_id,true);
		$criteria->compare('status',$this->status,true);
		$criteria->compare('quest_vars',$this->quest_vars,true);
		$criteria->compare('complete_count',$this->complete_count,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}