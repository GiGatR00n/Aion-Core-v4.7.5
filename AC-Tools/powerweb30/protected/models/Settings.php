<?php







/**
 * This is the model class for table "settings".
 *
 * The followings are the available columns in table 'settings':
 * @property integer $id
 * @property integer $access_level_admin
 * @property integer $access_level_editor
 * @property integer $hide_top
 * @property integer $page_news
 * @property integer $page_shop
 * @property integer $page_top
 * @property integer $referal_bonus
 * @property integer $referal_bonus_ref
 * @property integer $referal_enable
 * @property integer $referal_level
 * @property integer $trial_days
 * @property integer $trial_enable
 * @property integer $trial_type
 * @property integer $email_activation
 */
class Settings extends CActiveRecord
{
	/**
	 * Returns the static model of the specified AR class.
	 * @return Settings the static model class
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
		return 'settings';
	}

	/**
	 * @return array validation rules for model attributes.
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('access_level_admin, access_level_editor, hide_top, page_news, page_shop, page_top, referal_bonus, referal_bonus_ref, referal_enable, referal_level, trial_days, trial_enable, trial_type, email_activation', 'required'),
			array('access_level_admin, access_level_editor, hide_top, page_news, page_shop, page_top, referal_bonus, referal_bonus_ref, referal_enable, referal_level, trial_days, trial_enable, trial_type, email_activation', 'numerical', 'integerOnly'=>true),
			// The following rule is used by search().
			// Please remove those attributes that should not be searched.
			array('id, access_level_admin, access_level_editor, hide_top, page_news, page_shop, page_top, referal_bonus, referal_bonus_ref, referal_enable, referal_level, trial_days, trial_enable, trial_type, email_activation', 'safe', 'on'=>'search'),
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
			'access_level_admin' => 'Admin access level',
			'access_level_editor' => 'Editor access level',
			'hide_top' => 'Hide from top players',
			'page_news' => 'Total news articles',
			'page_shop' => 'Total products',
			'page_top' => 'Total of players on this page',
			'referal_bonus' => 'Bonus points from referral',
			'referal_bonus_ref' => 'Referral points',
			'referal_enable' => 'Enable referrals',
			'referal_level' => 'Minimum level for a player to receive bonuses',
			'trial_days' => 'Total of trial days, dependent on which temporary privilege assigned',
			'trial_enable' => 'Enable trials, including temporary privileges for new accounts',
			'trial_type' => 'Temporary privilege types',
			'email_activation' => 'Account activation via email',
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
		$criteria->compare('access_level_admin',$this->access_level_admin);
		$criteria->compare('access_level_editor',$this->access_level_editor);
		$criteria->compare('hide_top',$this->hide_top);
		$criteria->compare('page_news',$this->page_news);
		$criteria->compare('page_shop',$this->page_shop);
		$criteria->compare('page_top',$this->page_top);
		$criteria->compare('referal_bonus',$this->referal_bonus);
		$criteria->compare('referal_bonus_ref',$this->referal_bonus_ref);
		$criteria->compare('referal_enable',$this->referal_enable);
		$criteria->compare('referal_level',$this->referal_level);
		$criteria->compare('trial_days',$this->trial_days);
		$criteria->compare('trial_enable',$this->trial_enable);
		$criteria->compare('trial_type',$this->trial_type);
		$criteria->compare('email_activation',$this->email_activation);

		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}