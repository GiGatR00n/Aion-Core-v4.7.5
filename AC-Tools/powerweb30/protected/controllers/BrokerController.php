<?php







class BrokerController extends Controller
{
	public $layout='//content';
	
	
	public function actionIndex()
	{
		$this->pageTitle = Yii::t('title', 'Auctions');
		
		$criteria=new CDbCriteria;
		$criteria->select = 'item_id, item_count, seller, price, broker_race, expire_time'; // Fallenfate - These were broken because they were item_id, item_count, etc.
		$criteria->order = 'id DESC';
		$criteria->condition = 'is_sold = 0';
		
		$pages=new CPagination(Broker::model()->count($criteria));
		$pages->pageSize=50;
		$pages->applyLimit($criteria);
		
		$model = Broker::model()->findAll($criteria);
		
		$this->render('/broker', array(
			'model'=>$model,
			'pages' => $pages,
		));
	}

}