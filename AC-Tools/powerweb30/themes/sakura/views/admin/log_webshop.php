<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Покупка вещей</div>
		<table class="items">
			<tr>
				<th colspan=2>Купленная вещь</td>
				<th>Персонаж</td>
				<th>Кол-во</td>
				<th>Цена</td>
				<th>Дата</td>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td width="45px" align="left"><a class="aion-item-icon-large" href="http://aiondatabase.com/item/<?php echo $data['item']; ?>" target="_blank"></a></td>
				<td align="left"><a class="aion-item-text" href="http://aiondatabase.com/item/<?php echo $data['item']; ?>"></a></td>
				<td><?php echo $data['players']['name']; ?></td>
				<td><?php echo $data['amount']; ?></td>
				<td><?php echo $data['price']; ?></td>
				<td><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $data['date']); ?></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>

<div class="pages">
	<?php Config::pages($pages); ?>
</div>