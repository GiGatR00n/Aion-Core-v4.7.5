<?php

class Players extends CActiveRecord
{
	const SCENARIO_UB = 'userbar';
	
	
	public function getDbConnection(){
		return  Yii ::app()->gs;
	}

	/**
	 * Returns the static model of the specified AR class.
	 * @return Players the static model class
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
		return Config::db('gs').'.players';
	}
	
	
	/*
	* Правила валидации
	*/
	public function rules()
	{
		return array(
			array('id, name, account_id, account_name, x, y, z, heading, world_id, gender, race, player_class, show_inventory, show_location', 'required'),
			array('id, account_id, heading, world_id, npc_expands, advanced_stigma_slot_size, warehouse_size, mailbox_letters, bind_point, title_id, online, show_inventory, show_location', 'numerical', 'integerOnly'=>true),
			array('x, y, z', 'numerical'),
			array('name, account_name', 'length', 'max'=>50),
			array('name, account_name', 'match', 'pattern'=>'/^[A-Za-z0-9][\w]+$/'),
			array('exp, recoverexp', 'length', 'max'=>20),
			array('gender', 'length', 'max'=>6),
			array('race', 'length', 'max'=>9),
			array('player_class', 'length', 'max'=>13),
			array('creation_date, deletion_date, last_online, note', 'safe'),
			array('id, name, account_id, account_name, exp, recoverexp, x, y, z, heading, world_id, gender, race, player_class, creation_date, deletion_date, last_online, npc_expands, advanced_stigma_slot_size, warehouse_size, mailbox_letters, bind_point, title_id, online, note', 'safe', 'on'=>'search'),
			
			array('show_inventory, show_location', 'numerical', 'integerOnly'=>true, 'on'=>'settings'),
			
			array('name', 'unique', 'on'=>'admin_edit'),
		);
	}
	
	
	/*
	* Связи с таблицами
	*/
	public function relations()
	{
		return array(
			'accountData' => array(self::HAS_MANY, 'AccountData', 'id'),
			'lifeStat' => array(self::BELONGS_TO, 'PlayerLifeStats', 'id'),
			'abyssRank' => array(self::BELONGS_TO, 'AbyssRank', 'id'),
			'legionMembers' => array(self::HAS_ONE, 'LegionMembers', 'player_id'),
			'lifeStats' => array(self::HAS_ONE, 'PlayerLifeStats', 'player_id'),
			'logReferals' => array(self::HAS_ONE, 'Players', 'id'),
			/*'blocks' => array(self::HAS_MANY, 'Blocks', 'player'),
			'blocks1' => array(self::HAS_MANY, 'Blocks', 'blocked_player'),
			'friends' => array(self::HAS_MANY, 'Friends', 'player'),
			'friends1' => array(self::HAS_MANY, 'Friends', 'friend'),
			'itemCooldowns' => array(self::HAS_MANY, 'ItemCooldowns', 'player_id'),
			'playerAppearance' => array(self::HAS_ONE, 'PlayerAppearance', 'player_id'),
			'playerEffects' => array(self::HAS_MANY, 'PlayerEffects', 'player_id'),
			'playerMacrosses' => array(self::HAS_MANY, 'PlayerMacrosses', 'player_id'),
			'playerPunishments' => array(self::HAS_ONE, 'PlayerPunishments', 'player_id'),
			'playerQuests' => array(self::HAS_MANY, 'PlayerQuests', 'player_id'),
			'playerRecipes' => array(self::HAS_MANY, 'PlayerRecipes', 'player_id'),
			'playerSettings' => array(self::HAS_MANY, 'PlayerSettings', 'player_id'),
			'playerSkills' => array(self::HAS_MANY, 'PlayerSkills', 'player_id'),
			'playerTitles' => array(self::HAS_MANY, 'PlayerTitles', 'player_id'),*/
		);
	}
	
	
	/*
	* Название столбцов
	*/
	public function attributeLabels()
	{
		return array(
			'id' => 'ID',
			'name' => 'Name',
			'account_id' => 'Account',
			'account_name' => 'Account',
			'exp' => 'Exp',
			'lvl' => 'Level',
			'recoverexp' => 'Recover Exp',
			'x' => 'X',
			'y' => 'Y',
			'z' => 'Z',
			'heading' => 'Heading',
			'world_id' => 'World',
			'gender' => 'Gender',
			'race' => 'Race',
			'player_class' => 'Class',
			'creation_date' => 'Creation Date',
			'deletion_date' => 'Deletion Date',
			'last_online' => 'Last Online',
			'npc_expands' => 'Cube size',
			'advanced_stigma_slot_size' => 'Stigma slots',
			'warehouse_size' => 'Warehouse size',
			'mailbox_letters' => 'Mailbox Letters',
			'bind_point' => 'Bind point',
			'title_id' => 'Title',
			'online' => 'Status',
			'note' => 'Note',
			'show_inventory' => 'Show inventory',
			'show_location' => 'Show location',
			'ap' => 'AP',
			'all_kill' => 'Kills',
		);
	}
	
	
	/*
	* List of characters on the Player search page
	*/
	public function search()
	{
		$criteria=new CDbCriteria;
		$criteria->compare('id',$this->id);
		$criteria->compare('name',$this->name,true);
		$criteria->compare('account_id',$this->account_id);
		$criteria->compare('account_name',$this->account_name,true);
		$criteria->compare('race',$this->race,true);
		$criteria->compare('player_class',$this->player_class,true);
		
		return new CActiveDataProvider('Players', array(
			'criteria'=>$criteria,
			'sort'=>array(
				'defaultOrder'=>'name',
			),
			'pagination' => array(
				'pageSize' => 50,
			),
		));
	}
	
	
	/*
	* Character search on the Top Abyss players page
	*/
	public function topSearch()
	{
		$criteria=new CDbCriteria;
		$criteria->compare('id',$this->id);
		$criteria->compare('name',$this->name,true);
		$criteria->compare('race',$this->race,true);
		$criteria->compare('player_class',$this->player_class,true);
		$criteria->with = array('abyssRank');
		$criteria->condition = 'ap > 0';
		
		return new CActiveDataProvider('Players', array(
			'criteria'=>$criteria,
			'sort'=>array(
				'attributes'=>array(
					'name',
					'exp',
					'all_kill'=>array(
						'asc'=>'abyssRank.all_kill',
						'desc'=>'abyssRank.all_kill DESC',
					),
					'ap'=>array(
						'asc'=>'abyssRank.ap',
						'desc'=>'abyssRank.ap DESC',
					),
					'race',
					'player_class',
					'online',
				),
				'defaultOrder'=>'all_kill DESC',
			),
			'pagination' => array(
				'pageSize' => Config::get('page_top'),
				'params' => array (
					'firstPageLabel' => 'First page',
					'lastPageLabel' => 'Last page',
				),
			),
		));
	}
	
	
	/*
	* Returns a list of all account characters
	*/
	public function getPlayers()
	{
		$criteria=new CDbCriteria;
		$criteria->select = 'id, name, exp, race, player_class, creation_date';
		$criteria->condition = 'account_id = '.Yii::app()->user->id;
		return Players::model()->findAll($criteria);
	}
}