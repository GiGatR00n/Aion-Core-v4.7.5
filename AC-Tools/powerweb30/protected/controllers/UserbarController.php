<?php







class UserbarController extends Controller
{
	public $layout='//content';
	
	
	// 
	public function actionIndex()
	{
		if (Yii::app()->user->isGuest) $this->redirect(Yii::app()->homeUrl);
		
		$this->pageTitle = Yii::t('title', 'Userbar creation');
		
		$image = null;
		$model = new UserbarForm;
		
		if(isset($_POST['UserbarForm']))
		{
			$model->attributes = $_POST['UserbarForm'];
			
			if ($model->validate())
			{
				$criteria=new CDbCriteria;
				$criteria->select = 'id, name, exp, gender, race, player_class';
				$player = Players::model()->with('abyssRank', 'lifeStats')->findByPK($model->player_id);
				
				$criteria=new CDbCriteria;
				$criteria->select = 'name';
				$legion = Legions::model()->with(array(
					'legionMembers'=>array(
						'joinType'=>'INNER JOIN',
						'condition'=>'player_id = '.$model->player_id,
					),
				))->find();
				if($legion == NULL) $legion->name = null;
				
				Yii::app()->ih
					->load($_SERVER['DOCUMENT_ROOT'].Yii::app()->baseUrl.'/images/userbars/ub'.$model->fon.'.png')
					->text($player->name, Yii::app()->basePath.'/fonts/romic.ttf', 14, $this->rgb($model->shadow), CImageHandler::CORNER_LEFT_TOP, 8, 8)
					->text($player->name, Yii::app()->basePath.'/fonts/romic.ttf', 14, $this->rgb($model->name), CImageHandler::CORNER_LEFT_TOP, 8, 7)
					->text($legion->name, Yii::app()->basePath.'/fonts/monaco.ttf', 10, $this->rgb($model->shadow), CImageHandler::CORNER_LEFT_TOP, 8, 36)
					->text($legion->name, Yii::app()->basePath.'/fonts/monaco.ttf', 10, $this->rgb($model->legion), CImageHandler::CORNER_LEFT_TOP, 8, 35)
					->text(Info::class_text($player->player_class).', '.$this->genderRace($player->gender, $player->race), Yii::app()->basePath.'/fonts/verdana.ttf', 8, $this->rgb($model->shadow), CImageHandler::CORNER_LEFT_BOTTOM, 8, 9)
					->text(Info::class_text($player->player_class).', '.$this->genderRace($player->gender, $player->race), Yii::app()->basePath.'/fonts/verdana.ttf', 8, $this->rgb($model->class_race), CImageHandler::CORNER_LEFT_BOTTOM, 8, 10)
					->text(Info::lvl($player->exp).' Level', Yii::app()->basePath.'/fonts/romic.ttf', 14, $this->rgb($model->shadow), CImageHandler::CORNER_RIGHT_TOP, 8, 6)
					->text(Info::lvl($player->exp).' Level', Yii::app()->basePath.'/fonts/romic.ttf', 14, $this->rgb($model->level), CImageHandler::CORNER_RIGHT_TOP, 8, 5)
					->text($player->abyssRank->all_kill.'/'.$player->abyssRank->weekly_kill.' Kills', Yii::app()->basePath.'/fonts/monaco.ttf', 10, $this->rgb($model->shadow), CImageHandler::CORNER_RIGHT_TOP, 8, 34)
					->text($player->abyssRank->all_kill.'/'.$player->abyssRank->weekly_kill.' Kills', Yii::app()->basePath.'/fonts/monaco.ttf', 10, $this->rgb($model->kills), CImageHandler::CORNER_RIGHT_TOP, 8, 33)
					->text($player->lifeStats->hp.' HP, '.$player->lifeStats->mp.' MP', Yii::app()->basePath.'/fonts/verdana.ttf', 8, $this->rgb($model->shadow), CImageHandler::CORNER_RIGHT_BOTTOM, 8, 9)
					->text($player->lifeStats->hp.' HP, '.$player->lifeStats->mp.' MP', Yii::app()->basePath.'/fonts/verdana.ttf', 8, $this->rgb($model->stats), CImageHandler::CORNER_RIGHT_BOTTOM, 8, 10)
					->save($_SERVER['DOCUMENT_ROOT'].Yii::app()->baseUrl.'/userbars/'.$model->player_id.'.png');
					
				$image =  Yii::app()->homeUrl.'userbars/'.$model->player_id.'.png';
			}
		}
		
		$this->render('/userbar', array(
			'model' => $model,
			'players' => Players::getPlayers(),
			'image' => $image,
		));
	}
	
	
	public function genderRace($gender, $race)
	{
		if ($gender == 'MALE'AND $race == 'ELYOS') return  Yii::t('main', 'ELYOS');
		elseif ($gender == 'MALE'AND $race == 'ASMODIANS') return Yii::t('main', 'ASMODIANS');
		elseif ($gender == 'MALE'AND $race == 'ASMODIAN') return Yii::t('main', 'ASMODIAN');
		elseif ($gender == 'FEMALE'AND $race == 'ELYOS') return Yii::t('main', 'ELYOS');
		elseif ($gender == 'FEMALE'AND $race == 'ASMODIAN') return Yii::t('main', 'ASMODIAN');
		elseif ($gender == 'FEMALE'AND $race == 'ASMODIAN') return Yii::t('main', 'ASMODIAN');
		else return '???';
	}
	
	
	function rgb($hexStr, $returnAsString = false, $seperator = ',') {
		$hexStr = preg_replace("/[^0-9A-Fa-f]/", '', $hexStr);
		$rgbArray = array();
		if (strlen($hexStr) == 6) {
			$colorVal = hexdec($hexStr);
			$rgbArray['0'] = 0xFF & ($colorVal >> 0x10);
			$rgbArray['1'] = 0xFF & ($colorVal >> 0x8);
			$rgbArray['2'] = 0xFF & $colorVal;
		} elseif (strlen($hexStr) == 3) {
			$rgbArray['0'] = hexdec(str_repeat(substr($hexStr, 0, 1), 2));
			$rgbArray['1'] = hexdec(str_repeat(substr($hexStr, 1, 1), 2));
			$rgbArray['2'] = hexdec(str_repeat(substr($hexStr, 2, 1), 2));
		} else {
			return false;
		}
		return $returnAsString ? implode($seperator, $rgbArray) : $rgbArray;
	}
	
}