<?php

/**
 * This is the model class for table "aion_gs.legions".
 *
 * The followings are the available columns in table 'aion_gs.legions':
 * @property integer $id
 * @property string $name
 * @property integer $rank
 * @property integer $oldrank
 * @property integer $level
 * @property integer $contribution_points
 * @property integer $legionar_permission2
 * @property integer $centurion_permission1
 * @property integer $centurion_permission2
 * @property integer $disband_time
 */
class Legions extends CActiveRecord
{
	//public $_id;
	
	public function getDbConnection(){
		return  Yii::app()->gs;
	}
	
	public static function model($className=__CLASS__)
	{
		return parent::model($className);
	}
	
	/*
	* Название таблицы
	*/
	public function tableName()
	{
		return Config::db('gs').'.legions';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('id, name', 'required'),
			array('id, rank, oldrank, level, contribution_points, legionar_permission2, centurion_permission1, centurion_permission2, disband_time', 'numerical', 'integerOnly'=>true),
			array('name', 'length', 'max'=>16),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('id, name, rank, oldrank, level, contribution_points, legionar_permission2, centurion_permission1, centurion_permission2, disband_time', 'safe', 'on'=>'search'),
			
			array('id, name, rank, level, contribution_points', 'required', 'on'=>'admin_edit'),
			array('name', 'match', 'pattern'=>'/^[A-Za-z0-9][\w]+$/', 'on'=>'admin_edit'),
			array('name', 'unique', 'on'=>'admin_edit'),
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
			'membersCount' => array(self::STAT, 'LegionMembers', 'legion_id'),
			'legat' => array(self::HAS_ONE, 'LegionMembers', 'legion_id', 'condition' => 'legat.rank = "BRIGADE_GENERAL"', 'with' => 'players'),
			'legionMembers' => array(self::HAS_MANY, 'LegionMembers', 'legion_id', 'with' => 'players'),
			'legionHistory' => array(self::HAS_ONE, 'LegionHistory', 'legion_id', 'condition' => 'legionHistory.history_type = "CREATE"'),
			'abyssRank' => array(self::HAS_MANY, 'LegionMembers', 'player_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'id' => 'ID',
			'name' => 'Name',
			'rank' => 'Rank',
			'oldrank' => 'Old rank',
			'level' => 'Level',
			'contribution_points' => 'Contribution points',
			'legionar_permission2' => 'Legionar Permission2',
			'centurion_permission1' => 'Centurion Permission1',
			'centurion_permission2' => 'Centurion Permission2',
			'disband_time' => 'Disband Time',
			'membersCount' => 'Member count ',
		);
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 * @return CActiveDataProvider the data provider that can return the models based on the search/filter conditions.
	 */
	public function search()
	{
		$criteria=new CDbCriteria;
		
		$criteria->compare('t.id',$this->id);
		$criteria->compare('t.name',$this->name,true);
		$criteria->with = array('membersCount');
		//$criteria->with = array('legat.players');
		//$criteria->compare('rank',$this->rank);
		//$criteria->compare('oldrank',$this->oldrank);
		//$criteria->compare('level',$this->level);
		//$criteria->compare('contribution_points',$this->contribution_points);
		//$criteria->compare('legionar_permission2',$this->legionar_permission2);
		//$criteria->compare('centurion_permission1',$this->centurion_permission1);
		//$criteria->compare('centurion_permission2',$this->centurion_permission2);
		//$criteria->compare('disband_time',$this->disband_time);
		
		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
			

			'sort'=>array(
				'defaultOrder'=>'t.name',
			),
			'pagination' => array(
				'pageSize' => 50,
			),
		));
	}
}