<?php

class UserIdentity extends CUserIdentity
{
	private $_id;
	const ERROR_USER_INACTIVE = 3;
	
	
	public function authenticate()
	{
		$user = AccountData::model()->findByAttributes(array('name'=>$this->username));
		
		if ($user===null)
			$this->errorCode=self::ERROR_USERNAME_INVALID;
		else if (!$user->validatePassword($this->password))
			$this->errorCode=self::ERROR_PASSWORD_INVALID;
		elseif ($user->activated == 0)
			$this->errorCode=self::ERROR_PASSWORD_INVALID;
		else
		{
			$this->_id=$user->id;
			$this->username=$user->name;
			$this->setState('access_level', $user->access_level);
			$this->errorCode=self::ERROR_NONE;
		}
		return !$this->errorCode;
	}

	/**
	 * @return integer the ID of the user record
	 */
	public function getId()
	{
		return $this->_id;
	}
}