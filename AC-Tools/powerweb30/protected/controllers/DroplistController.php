<?php







class DroplistController extends Controller
{
	public $layout='//content';
	
	public function actions() {
		return Config::captcha();
	}
	
	
	public function actionIndex()
	{
		$this->pageTitle = Yii::t('title', 'Server droplist');
		
		$form = new SearchForm();
		$form->scenario = 'droplist';
		$model = null;
		
		if(isset($_POST['SearchForm']))
		{
			$form->attributes = $_POST['SearchForm'];
			
			if ($form->validate())
			{
				if (!empty($form->mobId) AND !empty($form->item_id)) {
					$where = 'mobId = '.$form->mobId.' AND itemid = '.$form->item_id;
				}
				elseif (!empty($form->mobId)) {
					$where = 'mobId = '.$form->mobId;
				}
				elseif (!empty($form->item_id)) {
					$where = 'item_id = '.$form->item_id;
				}
				else {
					$where = null;
				}
				
				$criteria=new CDbCriteria;
				$criteria->select = 'mobId, item_id, min, max, chance';
				$criteria->condition = $where;
				$criteria->order = 'chance DESC, max DESC';
				$criteria->limit = 100;
				$model = Droplist::model()->findAll($criteria);
			}
		}
		
		$this->render('/droplist', array(
			'form' => $form,
			'model' => $model,
		));
	}
	
}