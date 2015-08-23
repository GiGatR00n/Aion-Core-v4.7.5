<?php







/**
 * This is the model class for table "webshop".
 *
 * The followings are the available columns in table 'webshop':
 * @property integer $item_id
 * @property integer $level
 * @property string $name
 * @property integer $category_id
 * @property integer $amount
 * @property integer $price
 * @property integer $edit
 */
class Webshop extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return Webshop the static model class
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
		return 'webshop';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('item_id, name, category_id, price', 'required'),
			array('item_id, level, category_id, amount, price, edit', 'numerical', 'integerOnly'=>true),
			array('name', 'length', 'max'=>128),
			array('item_id', 'unique'),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('item_id, level, name, category_id, amount, price, edit', 'safe', 'on'=>'search'),
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
			'category' => array(self::BELONGS_TO, 'WebshopCategory', 'category_id'),
		);
	}

	/**
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'item_id' => 'Item ID',
			'level' => 'Level',
			'name' => 'Name',
			'category_id' => 'Category ID',
			'amount' => 'Amount',
			'price' => 'Price',
			'edit' => 'Change quantity',
		);
	}

	/**
	 * Retrieves a list of models based on the current search/filter conditions.
	 * @return CActiveDataProvider the data provider that can return the models based on the search/filter conditions.
	 */
	public function search()
	{
		$criteria=new CDbCriteria;
		
		$criteria->compare('t.name', $this->name, true);
		$criteria->with = array('category');
		
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