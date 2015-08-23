<div class="note">
	<div class="note_body">
		<div class="note_title">Аукцион</div>
		<table class="items">
			<tr>
				<th colspan=2>Товар</td>
				<th width="95px">Цена</td>
				<th>Кол-во</td>
				<th width="95px">Продавец</td>
				<th>Раса</td>
				<th width="110px">Истекает</td>
			</tr>
			<?php foreach ($model as $data) : ?>
			<tr class="center">
				<td width="45px" align="left"><a class="aion-item-icon-large" href="http://aiondatabase.com/item/<?php echo $data['item_id']; ?>" target="_blank"></a></td>
				<td align="left"><a class="aion-item-text" href="http://aiondatabase.com/item/<?php echo $data['item_id']; ?>"></a></td>
				<td><?php echo number_format($data['price'],0,' ',' '); ?></td>
				<td><?php echo $data['item_count']; ?></td>
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$data['seller']; ?>"><?php echo $data['seller']; ?></a></td>
				<td><?php echo Info::race($data['broker_race']); ?></td>
				<td><?php echo Config::datetime($data['expire_time']); ?></td>
			</tr>
			<?php endforeach ?>
		</table>
	</div>
</div>

<div class="pages">
	<?php Config::pages($pages); ?>
</div>