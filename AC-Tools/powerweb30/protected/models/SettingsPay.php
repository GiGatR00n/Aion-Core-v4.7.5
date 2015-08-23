<?php







class SettingsPay extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return SettingsPay the static model class
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
		return 'settings_pay';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('mrh_login, mrh_pass1, mrh_pass2, ik_shop_id, secret_key', 'length', 'max'=>128),
			array('inv_desc, ik_payment_desc', 'length', 'max'=>255),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('id, mrh_login, mrh_pass1, mrh_pass2, inv_desc, ik_shop_id, secret_key, ik_payment_desc', 'safe', 'on'=>'search'),
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
			'id' => 'ID',
			'mrh_login' => 'Log-in',
			'mrh_pass1' => 'Payments password #1',
			'mrh_pass2' => 'Payments password #2',
			'inv_desc' => 'Order description',
			'ik_shop_id' => 'Store ID (ik_shop_id)',
			'secret_key' => 'Secret key (secret_key)',
			'ik_payment_desc' => 'Total payment',
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
		$criteria->compare('mrh_login',$this->mrh_login,true);
		$criteria->compare('mrh_pass1',$this->mrh_pass1,true);
		$criteria->compare('mrh_pass2',$this->mrh_pass2,true);
		$criteria->compare('inv_desc',$this->inv_desc,true);
		$criteria->compare('ik_shop_id',$this->ik_shop_id,true);
		$criteria->compare('secret_key',$this->secret_key,true);
		$criteria->compare('ik_payment_desc',$this->ik_payment_desc,true);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}