<?php







/**
 * This is the model class for table "aion_ls.account_data".
 *
 * The followings are the available columns in table 'aion_ls.account_data':
 * @property integer $id
 * @property string $name
 * @property string $password
 * @property integer $activated
 * @property integer $access_level
 * @property integer $membership
 * @property integer $last_server
 * @property string $last_ip
 * @property string $ip_force
 * @property string $expire
 * @property integer $toll
 * @property string $email
 * @property integer $show_inventory
 * @property integer $show_location
 */
class AccountData extends CActiveRecord
{
    //public $MONEY = $this->params->money;
	//public $money;
	
    //const SCENARIO_LOST = 'lost';
	//const SCENARIO_EDIT = 'edit';
	
    //public $new_password;
    public $password_repeat;
    public $new_password;
    public $current_password;
	public $captcha;
	
	
	public function getDbConnection(){
		return  Yii::app()->ls;
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
		return Config::db('ls').'.account_data';
	}
	
	
	// Правила проверки входящих данных
	public function rules()
	{
		return array(
			//array('name, password, email', 'required'),
			//array('activated, access_level, membership, last_server, toll', 'numerical', 'integerOnly'=>true),
			
			array('name', 'match', 'pattern'=>'/^[A-Za-z0-9][\w]+$/'),
			array('name', 'length', 'min'=>3, 'max'=>45),
			array('password', 'length', 'min'=>3, 'max'=>65),
			array('password_repeat', 'length', 'min'=>3, 'max'=>65),
			array('new_password', 'length', 'min'=>3, 'max'=>65),
			array('current_password', 'length', 'min'=>3, 'max'=>65),
			array('email', 'email'),
			array('email', 'length', 'min'=>6, 'max'=>64),
			array('email', 'filter', 'filter'=>'mb_strtolower'),
			array('last_ip, ip_force', 'length', 'max'=>20),
			array('expire', 'safe'),
			
			array('name', 'unique', 'on'=>'register'),
			array('email', 'unique', 'on'=>'register'),
			array('captcha', 'captcha', 'allowEmpty'=>!extension_loaded('gd'), 'on'=>'register'),
			array('password_repeat, email, captcha', 'required', 'on'=>'register'),
			array('password', 'compare', 'compareAttribute'=>'password_repeat', 'on'=>'register'),
			
			array('id, name, password, activated, access_level, membership, last_ip, ip_force, expire, toll, email', 'safe', 'on'=>'search'),
			
			array('current_password', 'required', 'on'=>'edit'),
			array('new_password', 'compare', 'compareAttribute'=>'password_repeat', 'on'=>'edit'),
			array('email', 'email', 'on'=>'edit'),
			array('id, name, password, activated, access_level, membership, last_ip, ip_force, expire, toll', 'safe', 'on'=>'edit'),
			
			array('id, name, password, activated, access_level, membership, last_ip, ip_force, expire, toll, email', 'safe', 'on'=>'membership'),
			
			array('name, email, captcha', 'required', 'on'=>'lost'),
			array('id, new_password, password, activated, access_level, membership, last_ip, ip_force, expire, toll', 'safe', 'on'=>'lost'),
			
			array('toll', 'required', 'on'=>'balance'),
			
			array('name, activated, access_level, membership, toll', 'required', 'on'=>'admin_edit'),
			array('name', 'unique', 'on'=>'admin_edit'),
			array('email', 'unique', 'on'=>'admin_edit'),
		);
	}
	
	public function safeAttributes()
    {
		//return array('access_level');
    }

	/**
	 * @return array relational rules.
	 */
	public function relations()
	{
		// NOTE: you may need to adjust the relation name and the related
		// class name for the relations automatically generated below.
		return array(
			'players' => array(self::HAS_ONE, 'Players', 'account_id'),
			
			
			//'master' => array(self::MANY_MANY, 'Players', 'LogReferals(name, master)'),
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
			'password' => 'Password',
			'password_repeat' => 'Confirm password',
			'new_password' => 'New password',
			'current_password' => 'Current password',
			'activated' => 'Activated',
			'access_level' => 'Access level',
			'membership' => 'Membership',
			'last_server' => 'Last Server',
			'last_ip' => 'Last IP',
			'ip_force' => 'IP force',
			'expire' => 'Expire',
			'toll' => 'Donate',
			'email' => 'Email address',
			'captcha' => 'Captcha',
		);
	}
	
	
	public function search()
	{
		$criteria=new CDbCriteria;
		
		$criteria->compare('id',$this->id);
		$criteria->compare('name',$this->name,true);
		//$criteria->compare('access_level',$this->access_level);
		//$criteria->compare('membership',$this->membership);
		$criteria->compare('last_ip',$this->last_ip);
		//$criteria->compare('toll',$this->toll);
		$criteria->compare('email',$this->email);
		
		return new CActiveDataProvider('AccountData', array(
			'criteria'=>$criteria,
			'sort'=>array(
				'defaultOrder'=>'name',
			),
			'pagination' => array(
				'pageSize' => 50,
			),
		));
	}
	
	
	// Метод, который будет вызываться до сохранения данных в БД
	protected function beforeSave()
	{
		if(parent::beforeSave()) {
			if($this->isNewRecord)
				$this->password = $this->hashPassword($this->password);
			return true;
		}
		return false;
	}
	
	// XZ
	public function validatePassword($password)
	{
		return $this->hashPassword($password)===$this->password;
	}
	public function hashPassword($password)
	{
		return base64_encode(sha1($password, TRUE));
	}
	

}