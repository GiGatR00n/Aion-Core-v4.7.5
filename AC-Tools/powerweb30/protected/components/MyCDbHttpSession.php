<?php

/**
 * MyCDbHttpSession
 * 
 * @package Yii
 * @author Twisted1919
 * @copyright 2011
 * @version 1.3
 * @access public
 */
class MyCDbHttpSession extends CDbHttpSession
{

	public $_compareIpBlocks=2;
    public $_compareIpAddress=false;
    public $_compareUserAgent=false;
    
	/**
	 * MyCDbHttpSession::createSessionTable()
	 * 
	 * @param mixed $db
	 * @param mixed $tableName
	 * @return
	 */
	protected function createSessionTable($db, $tableName)
	{
	   $sql="CREATE TABLE IF NOT EXISTS `{$tableName}` (
          `id` char(32) NOT NULL,
          `ip_address` int(10) unsigned NOT NULL DEFAULT '0',
          `user_agent` char(32) NOT NULL,
          `expire` int(11) DEFAULT NULL,
          `data` text,
          PRIMARY KEY (`id`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		$db->createCommand($sql)->execute();
	}

	/**
	 * MyCDbHttpSession::readSession()
	 * 
	 * @param mixed $id
	 * @return mixed $data on success, empty string on failure
	 */
	public function readSession($id)
	{
		$db=$this->getDbConnection();
        $toBind=array();
        
        $sql = "SELECT `data` FROM `{$this->sessionTableName}` WHERE `id`=:id ";
        $toBind[':id']=$id;
        
        if($this->getCompareIpAddress())
        {
            $sql.="AND `ip_address`=:ip ";
            if($this->getCompareIpBlocks() > 0)
                $toBind[':ip']=sprintf("%u", ip2long($this->getClientIpBlocks()));
            else
                $toBind[':ip']=sprintf("%u", ip2long(Yii::app()->request->getUserHostAddress()));
        }
        if($this->getCompareUserAgent())
        {
            $sql.="AND `user_agent`=:ua ";
            $toBind[':ua']=md5(Yii::app()->request->getUserAgent());
        }
        
        $sql.="AND `expire`>:expire LIMIT 1";
		$toBind[':expire']=time();

		$data=$db->createCommand($sql)->queryScalar($toBind);
		return (false === $data) ? '' : $data;
		
	}
	
	/**
	 * MyCDbHttpSession::writeSession()
	 * 
	 * @param mixed $id
	 * @param mixed $data
	 * @return boolean
	 */
	public function writeSession($id, $data)
	{
        try
        {
    		$db=$this->getDbConnection();
            $toBind=array();
            $expire=time() + $this->getTimeout();
            
            $sql = "SELECT `id` FROM `{$this->sessionTableName}` WHERE `id`=:id ";
            $toBind[':id']=$id;
            
            if($this->getCompareIpAddress())
            {
                $sql.="AND `ip_address`=:ip ";
                if($this->getCompareIpBlocks() > 0)
                    $toBind[':ip']=sprintf("%u", ip2long($this->getClientIpBlocks()));
                else
                    $toBind[':ip']=sprintf("%u", ip2long(Yii::app()->request->getUserHostAddress()));
            }
            if($this->getCompareUserAgent())
            {
                $sql.="AND `user_agent`=:ua ";
                $toBind[':ua']=md5(Yii::app()->request->getUserAgent());
            }
            $sql.='LIMIT 1';
            
            if(false===$db->createCommand($sql)->queryScalar($toBind))
            {
                $sql = "DELETE FROM `{$this->sessionTableName}` WHERE `id`=:id LIMIT 1";
    			$db->createCommand($sql)->bindValue(':id', $id)->execute();	
                
                $toBind=array();
                $sql = "INSERT INTO `{$this->sessionTableName}` (`id`";
                $toBind[':id']=$id;
                if($this->getCompareIpAddress())
                {
                    $sql.=",`ip_address`";
                    if($this->getCompareIpBlocks() > 0)
                        $toBind[':ip']=sprintf("%u", ip2long($this->getClientIpBlocks()));
                    else
                        $toBind[':ip']=sprintf("%u", ip2long(Yii::app()->request->getUserHostAddress()));
                }
                if($this->getCompareUserAgent())
                {
                    $sql.=",`user_agent`";
                    $toBind[':ua']=md5(Yii::app()->request->getUserAgent());
                }
                $toBind[':expire']=$expire;
                $toBind[':data']=$data;
                
                $sql.=',`expire`,`data`) VALUES('.implode(',',array_keys($toBind)).')';
                $db->createCommand($sql)->execute($toBind);
            }
            else
            {
                $toBind=array();
                $sql = "UPDATE `{$this->sessionTableName}` SET `expire`=:expire, `data`=:data";
                $toBind[':expire']=$expire;
                $toBind[':data']=$data;
                
                if($this->getCompareIpAddress())
                {
                    $sql.=",`ip_address`=:ip";
                    if($this->getCompareIpBlocks() > 0)
                        $toBind[':ip']=sprintf("%u", ip2long($this->getClientIpBlocks()));
                    else
                        $toBind[':ip']=sprintf("%u", ip2long(Yii::app()->request->getUserHostAddress()));
                }
                if($this->getCompareUserAgent())
                {
                    $sql.=",`user_agent`=:ua";
                    $toBind[':ua']=md5(Yii::app()->request->getUserAgent());
                }
                $sql.=' WHERE `id`=:id LIMIT 1';
                $toBind[':id']=$id;
                
                $db->createCommand($sql)->execute($toBind);
            }          
        }
        catch(Exception $e)
        {
            if(YII_DEBUG)
				echo $e->getMessage();
			return false;
        }
        return true;
	}
	
	/**
	 * MyCDbHttpSession::getClientIpBlocks()
	 * 
	 * @return on success newly created ip based on block, on failure, localhost ip
     * 
     * Note, we could use a regular expression like:
     * /^([0-9]{1,3}+\.)([0-9]{1,3}+\.)([0-9]{1,3}+\.)([0-9]{1,3}+)$/
     * But, i think it's better this way because we have more control over the IP blocks.
	 */
	public function getClientIpBlocks()
	{
		$remoteIp=Yii::app()->request->getUserHostAddress();
		if(strpos($remoteIp,'.')!==false)
        {
            $blocks=explode('.',$remoteIp);
            $partialIp=array();
            $continue=false;
            $i=0;
            if(count($blocks)==4)
            {
                $continue=true;
                foreach($blocks AS $block)
                {
                    ++$i;
                    if(false===is_numeric($block)||$block<0||$block>255)
                    {
                        $continue=false;
                        break;
                    }
                    if($i<=$this->getCompareIpBlocks())
                        $partialIp[]=$block;
                    else
                        $partialIp[]=0;
                }
            }
            if($continue)
                return implode('.',$partialIp);
        }
        return '127.0.0.1';		
	}
    
    /**
	 * MyCDbHttpSession::setCompareIpBlocks()
	 * 
	 * @param int $int
	 */
    public function setCompareIpBlocks($int)
    {
        $int=(int)$int;
        if($int < 0)
            $this->_compareIpBlocks=0;
        elseif($int > 4)
            $this->_compareIpBlocks=4;
        else
            $this->_compareIpBlocks=$int;
    }
    
    /**
	 * MyCDbHttpSession::getCompareIpBlocks()
	 */
    public function getCompareIpBlocks()
    {
        return $this->_compareIpBlocks;
    }
    
    /**
	 * MyCDbHttpSession::setCompareIpAddress()
	 * 
	 * @param bool $bool
	 */
    public function setCompareIpAddress($bool)
    {
        $this->_compareIpAddress=(bool)$bool;
    }
    
    /**
	 * MyCDbHttpSession::getCompareIpAddress()
	 */
    public function getCompareIpAddress()
    {
        return $this->_compareIpAddress;
    }
    
    /**
	 * MyCDbHttpSession::setCompareUserAgent()
	 * 
	 * @param bool $bool
	 */
    public function setCompareUserAgent($bool)
    {
        $this->_compareUserAgent=(bool)$bool;
    }
    
    /**
	 * MyCDbHttpSession::getCompareUserAgent()
	 */
    public function getCompareUserAgent()
    {
        return $this->_compareUserAgent;
    }
	
}

?>