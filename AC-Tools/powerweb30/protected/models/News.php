<?php

class News extends CActiveRecord
{
	public function getDbConnection(){
		return  Yii ::app()->db;
	}
	
	/**
	 * Returns the static model of the specified AR class.
	 * @return News the static model class
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
		return Config::db('db').'.news';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('user_id, title, category_id, short_story', 'required'),
			array('user_id, category_id', 'numerical', 'integerOnly'=>true),
			array('short_story', 'length', 'max'=>65000),
			array('title', 'length', 'max'=>128),
			array('description, keywords', 'length', 'max'=>255),
			array('full_story, date', 'safe'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('news_id, user_id, title, category_id, short_story, full_story, date, description, keywords', 'safe', 'on'=>'search'),
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
			'category' => array(self::BELONGS_TO, 'NewsCategory', 'category_id'),
			'author'=>array(self::BELONGS_TO, 'AccountData', 'user_id'),
			//'author' => array(self::BELONGS_TO, 'AccountData', 'id', 'condition' => 'author.id = user_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'news_id' => 'ID',
			'user_id' => 'User',
			'title' => 'Title',
			'category_id' => 'Category',
			'short_story' => 'Short Story',
			'full_story' => 'Full Story',
			'date' => 'Date',
			'description' => 'Description',
			'keywords' => 'Keywords',
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

		$criteria->compare('news_id',$this->news_id);
		$criteria->compare('user_id',$this->user_id);
		$criteria->compare('title',$this->title,true);
		$criteria->compare('category_id',$this->category_id);
		$criteria->compare('short_story',$this->short_story,true);
		$criteria->compare('full_story',$this->full_story,true);
		$criteria->compare('date',$this->date,true);
		$criteria->compare('description',$this->description,true);
		$criteria->compare('keywords',$this->keywords,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}