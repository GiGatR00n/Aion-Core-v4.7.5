<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Проверка истекших привилегий</div>
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<table class="items">
			<tr>
				<th>Аккаунт</th>
				<th>Тип</th>
				<th>Дата</th>
			</tr>
		<?php if ($model) foreach($model as $data): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$data->name; ?>"><?php echo $data->name ;?></a></td>
				<td><?php echo $data->membership ;?></td>
				<td><?php echo $data->expire ;?></td>
			</tr>
		<?php endforeach; ?>
			<tr>
				<?php echo CHtml::beginForm(); ?>
				<td class="center" colspan="3"><input type="submit" class="button" name="Clear" value="Очистить"></td>
				<?php echo CHtml::endForm(); ?>
			</tr>
		</table>
	</div>
</div>