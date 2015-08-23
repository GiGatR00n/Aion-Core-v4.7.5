<?php $form=$this->beginWidget('CActiveForm'); ?>

<?php if ($model): ?>
	<table class="items">
		<?php foreach ($model as $gm): ?>
		<tr>
			<!--<td><?php echo Info::access_level_ico($gm->access_level); ?></td>-->
			<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$gm->players->name; ?>"><?php echo $gm->players->name; ?></a></td>
			<td align="right"><?php echo Info::race($gm->players->race); ?></td>
		</tr>
		<?php endforeach ?>
	</table>
<?php else: ?>
	<div class="menu_line">Number of GMs online: <span style="font-weight: bold">0</span></div>
<?php endif; ?>

<?php $this->endWidget(); ?>