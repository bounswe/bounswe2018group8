# Created by Umut Barış Öztunç
import re
from django.core import validators
from django.utils.deconstruct import deconstructible
from django.utils.translation import ugettext_lazy as _


@deconstructible
class FirstNameValidator(validators.RegexValidator):
    regex = r'^[^\W\d_]([^\W\d_]| )*$'
    message = _(
        'Enter a valid first name. This value may contain only letters and spaces.'
    )
    flags = re.UNICODE


@deconstructible
class LastNameValidator(validators.RegexValidator):
    regex = r'^[^\W\d_]+$'
    message = _(
        'Enter a valid last name. This value may contain only letters.'
    )
    flags = re.UNICODE

@deconstructible
class SkillValidator(validators.RegexValidator):
    regex = r'^[^\W\d_]([^\W\d_]| )*$'
    message = _(
        'Enter a valid skill. This value may contain only letters and spaces.'
    )
    flags = re.UNICODE


