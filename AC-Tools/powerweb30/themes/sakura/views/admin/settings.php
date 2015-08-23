<div class="note">
	<div class="note_body">
		<div class="note_title"><a href="<?php echo Yii::app()->homeUrl; ?>admin">Админцентр</a> &rarr; Настройка обвязки</div>
		<?php if(Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<?php echo CHtml::beginForm(); ?>
		<div class="form"><?php echo CHtml::errorSummary($form)?></div>
		
		<table class="table_info">
			<tr>
				<td class="bold">Общие настройки:</td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'access_level_admin'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'access_level_admin'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'access_level_editor'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'access_level_editor'); ?></td>
			</tr>
			<tr>
				<td><label for="Settings_hide_top">Скрывать игроков из Топов, с уровнем доступа выше</label></td>
				<td><?php echo CHtml::activeTextField($form,'hide_top'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'page_news'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'page_news'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'page_shop'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'page_shop'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'page_top'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'page_top'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form, 'email_activation'); ?></td>
				<td>
					<?php if ($form->email_activation == 1): ?>
						<input type="checkbox" id="Settings_email_activation" name="Settings[email_activation]" checked="checked">
					<?php else: ?>	
						<input type="checkbox" id="Settings_email_activation" name="Settings[email_activation]">
					<?php endif; ?>
				</td>
			</tr>
			<tr>
				<td class="bold">Система рефералов:</td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'referal_enable'); ?></td>
				<td>
					<?php if ($form->referal_enable == 1): ?>
						<input type="checkbox" id="Settings_referal_enable" name="Settings[referal_enable]" checked="checked">
					<?php else: ?>	
						<input type="checkbox" id="Settings_referal_enable" name="Settings[referal_enable]">
					<?php endif; ?>
				</td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'referal_level'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'referal_level'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'referal_bonus'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'referal_bonus'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'referal_bonus_ref'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'referal_bonus_ref'); ?></td>
			</tr>
			<tr>
				<td class="bold">Демо-привилегии:</td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'trial_enable'); ?></td>
				<td>
					<?php if ($form->trial_enable == 1): ?>
						<input type="checkbox" id="Settings_trial_enable" name="Settings[trial_enable]" checked="checked">
					<?php else: ?>	
						<input type="checkbox" id="Settings_trial_enable" name="Settings[trial_enable]">
					<?php endif; ?>
				</td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'trial_days'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'trial_days'); ?></td>
			</tr>
			<tr>
				<td><?php echo CHtml::activeLabel($form,'trial_type'); ?></td>
				<td><?php echo CHtml::activeTextField($form,'trial_type'); ?></td>
			</tr>
			<tr class="center">
				<td colspan="2"><input class="button" type="submit" value="Сохранить" name="yt0"></td>
			</tr>
		</table>
		<?php echo CHtml::endForm(); ?>
	</div>
</div>