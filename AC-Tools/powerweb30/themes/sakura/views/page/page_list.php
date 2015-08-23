<div class="note mb15">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Список добавленных страниц</div>
		<table class="items">
			<tr>
				<th>Название</th>
				<th>Заголовок</th>
				<th colspan="2">Операции</th>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'page/'.$data->name; ?>"><?php echo $data->name; ?></a></td>
				<td align="left"><?php echo $data->title; ?></td>
				<td width="36px"><a href="<?php echo Yii::app()->homeUrl.'page/edit/'.$data->id; ?>" /><img src="<?php echo Yii::app()->homeUrl; ?>images/edit.png" title="Редактировать" /></a></td>
				<td width="36px"><a href="<?php echo Yii::app()->homeUrl.'page/delete/'.$data->id; ?>" /><img src="<?php echo Yii::app()->homeUrl; ?>images/delete.png" title="Удалить" /></a></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>

<div class="pages">
	<?php Config::pages($pages); ?>
</div>