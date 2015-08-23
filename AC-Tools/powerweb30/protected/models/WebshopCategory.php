<?php







/**
 * This is the model class for table "webshop_category".
 *
 * The followings are the available columns in table 'webshop_category':
 * @property integer $category_id
 * @property string $name
 * @property string $alt_name
 */
class WebshopCategory extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return WebshopCategory the static model class
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
		return 'webshop_category';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('name, alt_name', 'required'),
			array('name, alt_name', 'length', 'max'=>32),
			array('name, alt_name', 'unique'),
			array('alt_name', 'match', 'pattern'=>'/^[A-Za-z0-9][\w]+$/'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('category_id, name, alt_name', 'safe', 'on'=>'search'),
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
			'category_id' => 'Category',
			'name' => 'Name',
			'alt_name' => 'Alternative name',
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

		$criteria->compare('category_id',$this->category_id);
		$criteria->compare('name',$this->name,true);
		$criteria->compare('alt_name',$this->alt_name,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}