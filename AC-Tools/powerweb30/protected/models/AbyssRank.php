<?php

/**
 * This is the model class for table "abyss_rank".
 *
 * The followings are the available columns in table 'abyss_rank':
 * @property integer $player_id
 * @property integer $daily_ap
 * @property integer $weekly_ap
 * @property integer $ap
 * @property integer $daily_gp
 * @property integer $weekly_gp
 * @property integer $gp
 * @property integer $rank
 * @property integer $top_ranking
 * @property integer $old_ranking
 * @property integer $daily_kill
 * @property integer $weekly_kill
 * @property integer $all_kill
 * @property integer $max_rank
 * @property integer $last_kill
 * @property integer $last_ap
 * @property string $last_update
 *
 * The followings are the available model relations:
 * @property Players $player
 */
class AbyssRank extends CActiveRecord
{
	public function getDbConnection(){
		return  Yii ::app()->gs;
	}

	/**
	 * Returns the static model of the specified AR class.
	 * @return AbyssRank the static model class
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
		return Config::db('gs').'.abyss_rank';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('player_id, daily_ap, daily_gp, weekly_ap, weekly_gp, ap, gp, daily_kill, weekly_kill, last_kill, last_ap, last_update', 'required'),
			array('player_id, daily_ap, daily_gp, weekly_ap, weekly_gp, ap, gp, rank, top_ranking, old_ranking, daily_kill, weekly_kill, all_kill, max_rank, last_kill, last_ap, last_gp', 'numerical', 'integerOnly'=>true),
			array('last_update', 'length', 'max'=>20),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('player_id, daily_ap, daily_gp, weekly_ap, weekly_gp, ap, gp, rank, top_ranking, old_ranking, daily_kill, weekly_kill, all_kill, max_rank, last_kill, last_ap, last_gp, last_update', 'safe', 'on'=>'search'),
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
			//'player' => array(self::BELONGS_TO, 'Players', 'player_id'),
			//'legionMembers' => array(self::HAS_ONE, 'LegionMembers', 'player_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'player_id' => 'Player',
			'daily_ap' => 'Daily Ap',
			'weekly_ap' => 'Weekly Ap',
			'gp' => 'GP',
			'daily_gp' => 'Daily Gp',
			'weekly_gp' => 'Weekly Gp',
			'ap' => 'AP',
			'rank' => 'Rank',
			'top_ranking' => 'Top Ranking',
			'old_ranking' => 'Old Ranking',
			'daily_kill' => 'Daily Kill',
			'weekly_kill' => 'Weekly Kill',
			'all_kill' => 'All Kill',
			'max_rank' => 'Max Rank',
			'last_kill' => 'Last Kill',
			'last_ap' => 'Last Ap',
			'last_gp' => 'Last Gp',
			'last_update' => 'Last Update',
		);
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 * @return CActiveDataProvider the data provider that can return the models based on the search/filter conditions.
	 */
	public function search()
	{
		$criteria=new CDbCriteria;
		$criteria->compare('player_id',$this->player_id);
		$criteria->compare('daily_ap',$this->daily_ap);
		$criteria->compare('weekly_ap',$this->weekly_ap);
		$criteria->compare('ap',$this->ap);
		$criteria->compare('daily_gp',$this->daily_gp);
		$criteria->compare('weekly_gp',$this->weekly_gp);
		$criteria->compare('gp',$this->gp);
		$criteria->compare('rank',$this->rank);
		$criteria->compare('top_ranking',$this->top_ranking);
		$criteria->compare('old_ranking',$this->old_ranking);
		$criteria->compare('daily_kill',$this->daily_kill);
		$criteria->compare('weekly_kill',$this->weekly_kill);
		$criteria->compare('all_kill',$this->all_kill);
		$criteria->compare('max_rank',$this->max_rank);
		$criteria->compare('last_kill',$this->last_kill);
		$criteria->compare('last_ap',$this->last_ap);
		$criteria->compare('last_gp',$this->last_gp);
		$criteria->compare('last_update',$this->last_update,true);
		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}