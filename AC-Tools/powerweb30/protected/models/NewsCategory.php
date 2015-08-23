<?php

/**
 * This is the model class for table "news_category".
 *
 * The followings are the available columns in table 'news_category':
 * @property integer $id
 * @property string $title
 * @property string $name
 *
 * The followings are the available model relations:
 * @property News[] $news
 */
class NewsCategory extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return NewsCategory the static model class
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
		return 'news_category';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('name, alt_name, title', 'required'),
			array('name, alt_name', 'length', 'max'=>32),
			array('title, description, keywords', 'length', 'max'=>255),
			array('alt_name', 'match', 'pattern'=>'/^[A-Za-z0-9][\w]+$/'),
			array('alt_name', 'unique'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('category_id, title, name', 'safe', 'on'=>'search'),
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
			//'name' => array(self::BELONGS_TO, 'News', 'id'),
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
			'alt_name' => 'Alternate name',
			'title' => 'Title',
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

		$criteria->compare('id',$this->id);
		$criteria->compare('title',$this->title,true);
		$criteria->compare('name',$this->name,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}