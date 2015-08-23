<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Проверка сдвоенных вещей</div>
		<table class="items">
			<tr>
				<th>Персонаж</th>
				<th>ID в инвентаре</th>
				<th>ID вещи</th>
				<th>ID доп. вещи</th>
				<th>Удалить</th>
			</tr>
		<?php foreach($model as $data): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$data->players->name; ?>"><?php echo $data->players->name ;?></a></td>
				<td><?php echo $data->itemUniqueId ;?></td>
				<td><?php echo $data->item_id ;?></td>
				<td><?php echo $data->fusionedItem ;?></td>
				<td width="36px"><a href="<?php echo Yii::app()->homeUrl.'admin/check/idelete/'.$data->itemUniqueId; ?>" /><img src="<?php echo Yii::app()->homeUrl; ?>images/delete.png" title="Удалить" /></a></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>