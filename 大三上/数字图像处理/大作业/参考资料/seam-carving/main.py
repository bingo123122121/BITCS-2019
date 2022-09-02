from seam_carving import SeamCarver

import os



def image_resize_without_mask(filename_input, filename_output, new_height, new_width):
    obj = SeamCarver(filename_input, new_height, new_width)
    obj.save_result(filename_output)


def image_resize_with_mask(filename_input, filename_output, new_height, new_width, filename_mask):
    obj = SeamCarver(filename_input, new_height, new_width, protect_mask=filename_mask)
    obj.save_result(filename_output)


def object_removal(filename_input, filename_output, filename_mask):
    obj = SeamCarver(filename_input, 0, 0, object_mask=filename_mask)
    obj.save_result(filename_output)



if __name__ == '__main__':
    """
    Put image in in/images folder and protect or object mask in in/masks folder
    Ouput image will be saved to out/images folder with filename_output
    """

    folder_in = 'example'
    folder_out = ''

    filename_input = '18.jpeg'
    filename_output = '18_result.png'
    filename_mask = 'mask.jpg'
    new_height = 400
    new_width = 550

    input_image = os.path.join(folder_in, "", filename_input)
    input_mask = os.path.join(folder_in, "", filename_mask)
    output_image = os.path.join(folder_out, "", filename_output)

    # if not os.path.exists(input_image):
    #     os.makedirs(input_image)
    # if not os.path.exists(input_mask):
    #     os.makedirs(input_mask)
    # if not os.path.exists(output_image):
    #     os.makedirs(output_image)

    print(input_image)
    print(output_image)

    image_resize_without_mask(input_image, output_image, new_height, new_width)
    #image_resize_with_mask(input_image, output_image, new_height, new_width, input_mask)
    #object_removal(input_image, output_image, input_mask)








