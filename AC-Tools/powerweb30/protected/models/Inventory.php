<?php

class Inventory extends CActiveRecord
{
	public $new_amount;
	
	
	public function getDbConnection(){
		return  Yii ::app()->gs;
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
		return 'inventory';
	}
	
	
	/*
	* Правила валидации
	*/
	public function rules()
	{
		return array(
			array('item_unique_id, item_id, item_color, item_owner, is_equiped, is_soul_bound, slot, item_location, enchant, item_skin, fusioned_item, optional_socket, optional_fusion_socket', 'numerical', 'integerOnly'=>true),
			array('item_count', 'length', 'max'=>20),
			
			// Поиск
			array('item_unique_id, item_id, item_count, item_color, item_owner, is_equiped, is_soul_bound, slot, item_location, enchant, item_skin, fusioned_item, optional_socket, optional_fusion_socket', 'safe', 'on'=>'search'),
			
			// Покупка вещей
			array('item_unique_id, item_count, item_color, is_equiped, is_soul_bound, slot, item_location, enchant, item_skin, fusioned_item, optional_socket, optional_fusion_socket', 'safe', 'on'=>'buy'),
			array('new_amount', 'numerical', 'integerOnly'=>true, 'min' => '1', 'on'=>'buy'),
			array('item_id, item_owner', 'required', 'on'=>'buy'),
		);
	}
	
	
	/*
	* Связи с таблицами
	*/
	public function relations()
	{
		return array(
			'players' => array(self::BELONGS_TO, 'Players', 'item_owner'),
		);
	}
	
	
	/*
	* Название столбцов
	*/
	public function attributeLabels()
	{
		return array(
			'item_unique_id' => 'Item Unique',
			'item_id' => 'Item',
			'item_count' => 'Item Count',
			'item_color' => 'Item Color',
			'item_owner' => 'Item Owner',
			'is_equiped' => 'Is Equipped',
			'is_soul_bound' => 'Is Soul Bound',
			'slot' => 'Slot',
			'item_location' => 'Item Location',
			'enchant' => 'Enchant',
			'item_skin' => 'Item Skin',
			'fusioned_item' => 'Fusioned Item',
			'optional_socket' => 'Optional Socket',
			'optional_fusion_socket' => 'Optional Fusion Socket',
		);
	}
	
	
	/*
	* Поиск
	*/
	public function search()
	{
		$criteria=new CDbCriteria;
		$criteria->compare('item_unique_id',$this->item_unique_id);
		$criteria->compare('item_id',$this->item_id);
		$criteria->compare('item_count',$this->item_count,true);
		$criteria->compare('item_color',$this->item_color);
		$criteria->compare('item_owner',$this->item_owner);
		$criteria->compare('is_equiped',$this->is_equiped);
		$criteria->compare('is_soul_bound',$this->is_soul_bound);
		$criteria->compare('slot',$this->slot);
		$criteria->compare('item_location',$this->item_location);
		$criteria->compare('enchant',$this->enchant);
		$criteria->compare('item_skin',$this->item_skin);
		$criteria->compare('fusioned_item',$this->fusioned_item);
		$criteria->compare('optional_socket',$this->optional_socket);
		$criteria->compare('optional_fusion_socket',$this->optional_fusion_socket);
		return new CActiveDataProvider($this, array(
			'criteria'=>$criteria,
		));
	}
}